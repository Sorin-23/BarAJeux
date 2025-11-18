import { TestBed } from '@angular/core/testing';

import { TableJeuService } from './table-jeu-service';

describe('TableJeuService', () => {
  let service: TableJeuService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(TableJeuService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
