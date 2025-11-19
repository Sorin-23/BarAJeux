import { ComponentFixture, TestBed } from '@angular/core/testing';

import { JeuPage } from './jeu-page';

describe('JeuPage', () => {
  let component: JeuPage;
  let fixture: ComponentFixture<JeuPage>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [JeuPage]
    })
    .compileComponents();

    fixture = TestBed.createComponent(JeuPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
