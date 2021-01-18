import { AfterViewInit, Component, OnDestroy, OnInit } from '@angular/core';
import { Map, MapOptions, latLng, tileLayer, Marker, Icon } from 'leaflet';

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
  shadowSize: [41, 41]
});
Marker.prototype.options.icon = iconDefault;

@Component({
  selector: 'app-map',
  templateUrl: './map.component.html',
  styleUrls: ['./map.component.scss']
})
export class MapComponent implements AfterViewInit {
  public options: MapOptions = {
    layers: [tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
      maxZoom: 19,
      minZoom: 3,
      attribution: '&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
    })],
    zoom: 8,
    center: latLng(44.304604, 20.838051)
  };
  public map: Map;
  public zoom: number;


  ngAfterViewInit(): void {
    this.initMap();

    const markers = [
      {
        lat: 44.80401,
        lon: 20.46513
      },
      {
        lat: 45.25167,
        lon: 19.83694
      },
      {
        lat: 44.01667,
        lon: 20.91667
      },
      {
        lat: 42.99806,
        lon: 21.94611
      },
      {
        lat: 43.13667,
        lon: 20.51222
      }
    ] 

    this.addMarkers(markers);
  }

  addMarkers(markers: any): void {
    for (const m of markers) {
      const lat = m.lat;
      const lon = m.lon;
      const marker = new Marker([lat, lon]).addTo(this.map);
    }
  }

  /*ngOnDestroy() {
    this.map.clearAllEventListeners;
    this.map.remove();
  };*/

  initMap() {
    this.map = new Map('map', this.options);
    this.map.removeControl(this.map.zoomControl);
  }


}
