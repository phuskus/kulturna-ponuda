import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-results',
  templateUrl: './results.component.html',
  styleUrls: ['./results.component.scss'],
})
export class ResultsComponent implements OnInit {
  public results: any = [
    {
      name: 'Museum of Modern Art',
      location: 'Dunavska 35, Novi Sad',
      category: 'Museum of art and history',
      openUntil: '7:00PM',
      description:
        'The museum is open, and many exhibits are available. Unfortunately, the planetarium is closed. The rainforest sphere and the shake house require special reseratiaos and a 2-4 hour wait. Sadly, not worth going during COVID, I would wait until restrictions are lifted to get the ful experience.',
    },
  ];
  constructor() {}

  ngOnInit(): void {}
}
