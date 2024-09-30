import {
  HttpClientTestingModule,
  HttpTestingController,
} from '@angular/common/http/testing';
import { TestBed } from '@angular/core/testing';
import { expect } from '@jest/globals';

import { UserService } from './user.service';
import { User } from '../interfaces/user.interface';

describe('UserService', () => {
  let service: UserService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [UserService],
    });

    service = TestBed.inject(UserService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should fetch the user detail', () => {
    const id = '1';
    const mockUser: User = {
      id: 1,
      email: 'test@example.com',
      lastName: 'test',
      firstName: 'test',
      admin: true,
      password: 'password',
      createdAt: new Date(),
    };

    service.getById(id).subscribe((res: User) => {
      expect(res).toEqual(mockUser);
    });

    const req = httpMock.expectOne(`api/user/${id}`);
    expect(req.request.method).toBe('GET');

    req.flush(mockUser);
  });

  it('should delete the user', () => {
    const id = '1';

    service.delete(id).subscribe();

    const req = httpMock.expectOne(`api/user/${id}`);
    expect(req.request.method).toBe('DELETE');

    req.flush(null);
  });
});
