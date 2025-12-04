import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EmpruntPage } from './emprunt-page';

describe('EmpruntPage', () => {
  let component: EmpruntPage;
  let fixture: ComponentFixture<EmpruntPage>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [EmpruntPage]
    })
    .compileComponents();

    fixture = TestBed.createComponent(EmpruntPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
