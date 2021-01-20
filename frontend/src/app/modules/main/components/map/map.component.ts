import { Router } from '@angular/router';
import { Subscription } from 'rxjs';
import {
  EventBusService,
  Events,
} from './../../../../services/event-bus/event-bus.service';
import { CulturalOffer } from 'src/app/model/CulturalOffer';
import { AfterViewInit, Component, OnDestroy, OnInit } from '@angular/core';
import {
  Map,
  MapOptions,
  latLng,
  tileLayer,
  Marker,
  Icon,
  MarkerOptions,
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
export class MapComponent implements OnInit, AfterViewInit {
  public options: MapOptions = {
    layers: [
      tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
        maxZoom: 19,
        minZoom: 3,
        attribution:
          '&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors',
      }),
    ],
    zoom: 8,
    center: latLng(44.304604, 20.838051),
  };
  public map: Map;
  public zoom: number;
  public markers: Marker[] = [];

  private eventBusSub: Subscription;

  constructor(private eventBus: EventBusService, private router: Router) {}

  ngOnInit(): void {
    this.eventBusSub = this.eventBus.on(Events.OfferListChange, (offers) =>
      this.addMarkers(offers)
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
        title: m.id.toString(),
      };
      const marker = new Marker([lat, lon], options).addTo(this.map);
      marker.on('click', this.offerClick, this);
      this.markers.push(marker);
    }
  }

  offerClick($event) {
    const id = $event.sourceTarget.options.title;
    this.router.navigate(['/offers/' + id]);
  }

  ngOnDestroy() {
    this.eventBusSub.unsubscribe();
    this.map.clearAllEventListeners;
    this.map.remove();
  }

  initMap() {
    this.map = new Map('map', this.options);
    this.map.removeControl(this.map.zoomControl);
  }
}
