import { ActivatedRoute, NavigationEnd, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import {
  EventBusService,
  Events,
} from 'src/app/services/event-bus/event-bus.service';
import { CulturalOffer } from 'src/app/shared/models/CulturalOffer';
import { AfterViewInit, Component, OnInit, OnDestroy } from '@angular/core';
import {
  Map,
  MapOptions,
  latLng,
  tileLayer,
  Marker,
  Icon,
  MarkerOptions,
  LatLng,
} from 'leaflet';

const iconRetinaUrl = 'assets/marker-icon-2x.png';
const iconUrl = 'assets/marker-icon.png';
const shadowUrl = 'assets/marker-shadow.png';
const iconDefault = new Icon({
  iconRetinaUrl,
  iconUrl,
  shadowUrl,
  iconSize: [25, 41],
  iconAnchor: [12, 41],
  popupAnchor: [1, -34],
  tooltipAnchor: [16, -28],
  shadowSize: [41, 41],
});
Marker.prototype.options.icon = iconDefault;

@Component({
  selector: 'app-map',
  templateUrl: './map.component.html',
  styleUrls: ['./map.component.scss'],
})
export class MapComponent implements OnInit, OnDestroy, AfterViewInit {
  public map: Map;
  public marginLeft: string = '0px';
  public markers: Marker[] = [];
  private initLatLng: LatLng = new LatLng(44.304604, 20.838051);
  private initZoom = 8;

  public options: MapOptions = {
    layers: [
      tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
        maxZoom: 19,
        minZoom: 3,
        attribution:
          '&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors',
      }),
    ],
    zoom: this.initZoom,
    center: this.initLatLng,
  };

  public displayInfo: string = 'hidden';
  public displayInfoLat: string = '0px';
  public displayInfoLon: string = '0px';

  private offerList: CulturalOffer[];
  public focusedOffer: CulturalOffer;

  private previousResultsPage: string = '';

  private subscriptions: Subscription[] = [];

  constructor(
    private eventBus: EventBusService,
    private router: Router
  ) {
    this.subscriptions.push(
      this.router.events
        .filter((e) => e instanceof NavigationEnd)
        .subscribe((e: NavigationEnd) => {
          if (e.url === '/') {
            this.marginLeft = '0px';
            if (this.map){
              this.map.setView(this.initLatLng, this.initZoom);
            }
            return; 
          } 
          if (e.url.includes('search?')) {
            this.previousResultsPage = e.url;
          }
        })
    );
  }

  ngOnInit(): void {
    this.subscriptions.push(
      this.eventBus.on(Events.OfferListChange, (offers: CulturalOffer[]) => {
        const lat =
          offers.reduce((acc, offer) => acc + offer.latitude, 0) /
          offers.length;
        const lon =
          offers.reduce((acc, offer) => acc + offer.longitude, 0) /
          offers.length;
        const latLng = new LatLng(lat, lon);
        this.map.setView(latLng, 9);
        this.offerList = offers;
        this.marginLeft = '350px';
        this.addMarkers(offers);
      })
    );
    this.subscriptions.push(
      this.eventBus.on(Events.OfferFocused, (offer: CulturalOffer) => {
        let latLng = new LatLng(offer.latitude, offer.longitude);
        this.map.setView(latLng, 16);
        this.marginLeft = '350px';
        if (!this.offerList) {
          this.addMarkers([offer]);
        }
      })
    );
  }

  ngAfterViewInit(): void {
    this.initMap();
  }

  addMarkers(offers: CulturalOffer[]): void {
    this.markers.forEach((m) => m.removeFrom(this.map));
    this.markers = [];
    for (const m of offers) {
      const lat = m.latitude;
      const lon = m.longitude;
      const options: MarkerOptions = {
        attribution: m.id.toString(),
      };
      const marker = new Marker([lat, lon], options);
      marker.on('click', this.offerClick, this);
      marker.on('mouseover', this.offerMouseOver, this);
      marker.on('mouseout', this.offerMouseOut, this);
      marker.addTo(this.map);
      this.markers.push(marker);
    }
  }

  offerClick($event) {
    const id = $event.sourceTarget.options.attribution;
    this.router.navigate(['/offers/' + id], {state: {previousRoute: this.previousResultsPage}});
  }

  offerMouseOver($event) {
    const id = $event.sourceTarget.options.attribution;
    this.focusedOffer = this.offerList.find((offer) => offer.id == id);
    this.displayInfo = 'visible';
    const margin: number = Number(this.marginLeft.split('px')[0]);
    this.displayInfoLat = ($event.containerPoint.x + margin).toString() + 'px';
    this.displayInfoLon = $event.containerPoint.y.toString() + 'px';
  }

  offerMouseOut($event) {
    this.displayInfo = 'hidden';
  }

  ngOnDestroy() {
    this.subscriptions.forEach((sub) => sub.unsubscribe());
    this.map.clearAllEventListeners;
    this.map.remove();
  }

  initMap() {
    this.map = new Map('map', this.options);
    this.map.removeControl(this.map.zoomControl);
  }
}
