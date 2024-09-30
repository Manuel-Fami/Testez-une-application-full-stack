import { TestBed } from '@angular/core/testing';
import { expect } from '@jest/globals';
import { ObserverSpy } from '@hirez_io/observer-spy';

import { SessionService } from './session.service';
import { SessionInformation } from '../interfaces/sessionInformation.interface';

describe('SessionService', () => {
  let service: SessionService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SessionService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  describe('logOut', () => {
    it('should update isLogged to false and sessionInformation to undefined when logOut is called', () => {
      // Given
      const user: SessionInformation = {
        token: 'testToken',
        type: 'testType',
        id: 1,
        username: 'testUsername',
        firstName: 'testFirstName',
        lastName: 'testLastName',
        admin: false,
      };

      const observerSpy = new ObserverSpy<boolean>();
      service.$isLogged().subscribe(observerSpy);

      // When
      service.logOut();

      // Then
      expect(service.isLogged).toBe(false);
      expect(service.sessionInformation).toBeUndefined();
      expect(observerSpy.getLastValue()).toBe(false);
    });
  });
});
