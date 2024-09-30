import { TestBed } from '@angular/core/testing';
import {
  HttpClientTestingModule,
  HttpTestingController,
} from '@angular/common/http/testing';
import { expect } from '@jest/globals';
import { SessionApiService } from './session-api.service';
import { Session } from '../interfaces/session.interface';

describe('SessionApiService', () => {
  let service: SessionApiService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [SessionApiService],
    });

    service = TestBed.inject(SessionApiService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should create a session and return the created session', () => {
    // Given : La session à créer
    const session: Session = {
      name: 'Test Session',
      description: 'This is a test session',
      date: new Date(),
      teacher_id: 1,
      users: [1, 2, 3],
    };

    // When : Appel de la méthode à tester avec la session
    service.create(session).subscribe((response) => {
      expect(response).toEqual(session);
    });

    // Then : Vérification de la requête
    const req = httpMock.expectOne('api/session');
    expect(req.request.method).toBe('POST');
    expect(req.request.body).toEqual(session);

    req.flush(session);
  });

  it('should update a session', () => {
    const id = '1';
    const updatedSession: Session = {
      id: 1,
      name: 'Updated Test Session',
      description: 'This is an updated test session',
      date: new Date(),
      teacher_id: 1,
      users: [1, 2, 3],
    };

    service.update(id, updatedSession).subscribe();

    const req = httpMock.expectOne(`api/session/${id}`);
    expect(req.request.method).toBe('PUT');
    expect(req.request.body).toEqual(updatedSession);

    req.flush(updatedSession);
  });

  it('should delete the session', () => {
    const id = '1';

    service.delete(id).subscribe();

    const req = httpMock.expectOne(`api/session/${id}`);
    expect(req.request.method).toBe('DELETE');

    req.flush(null);
  });

  it('should participate in a session', () => {
    const sessionId = '1';
    const userId = '1';

    service.participate(sessionId, userId).subscribe();

    const req = httpMock.expectOne(
      `api/session/${sessionId}/participate/${userId}`
    );
    expect(req.request.method).toBe('POST');

    req.flush(null);
  });

  it('should unparticipate from a session', () => {
    const sessionId = '1';
    const userId = '1';

    service.unParticipate(sessionId, userId).subscribe();

    const req = httpMock.expectOne(
      `api/session/${sessionId}/participate/${userId}`
    );
    expect(req.request.method).toBe('DELETE');

    req.flush(null);
  });
});
