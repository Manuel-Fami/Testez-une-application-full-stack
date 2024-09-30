import { TestBed } from '@angular/core/testing';
import { AuthService } from './auth.service';
import {
  HttpClientTestingModule,
  HttpTestingController,
} from '@angular/common/http/testing';
import { RegisterRequest } from '../interfaces/registerRequest.interface';
import { expect } from '@jest/globals';
import { LoginRequest } from '../interfaces/loginRequest.interface';
import { SessionInformation } from 'src/app/interfaces/sessionInformation.interface';

describe('AuthService', () => {
  let service: AuthService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [AuthService],
    });

    service = TestBed.inject(AuthService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should send a POST request to the register endpoint', () => {
    const registerRequest: RegisterRequest = {
      email: 'test@example.com',
      firstName: 'test',
      lastName: 'test',
      password: 'testpassword',
    };

    service.register(registerRequest).subscribe();

    const req = httpMock.expectOne('api/auth/register');
    expect(req.request.method).toBe('POST');
    expect(req.request.body).toEqual(registerRequest);

    req.flush(null);
  });

  it('should return an empty observable on successful registration', () => {
    const registerRequest: RegisterRequest = {
      email: 'test@example.com',
      firstName: 'test',
      lastName: 'test',
      password: 'testpassword',
    };

    service.register(registerRequest).subscribe((response) => {
      expect(response).toBeUndefined();
    });

    const req = httpMock.expectOne('api/auth/register');
    req.flush(null);
  });

  it('should send a POST request to the login endpoint', () => {
    const loginRequest: LoginRequest = {
      email: 'test@example.com',
      password: 'testpassword',
    };

    service.login(loginRequest).subscribe();

    const req = httpMock.expectOne('api/auth/login');
    expect(req.request.method).toBe('POST');
    expect(req.request.body).toEqual(loginRequest);

    req.flush(null);
  });

  it('should return session information on successful login', () => {
    const loginRequest: LoginRequest = {
      email: 'test@example.com',
      password: 'testpassword',
    };

    const sessionInfo: SessionInformation = {
      token: 'mockToken',
      type: 'mockType',
      id: 1,
      username: 'mockUsername',
      firstName: 'mockFirstName',
      lastName: 'mockLastName',
      admin: false,
    };

    service.login(loginRequest).subscribe((response) => {
      expect(response).toEqual(sessionInfo);
    });

    const req = httpMock.expectOne('api/auth/login');
    req.flush(sessionInfo);
  });
});
