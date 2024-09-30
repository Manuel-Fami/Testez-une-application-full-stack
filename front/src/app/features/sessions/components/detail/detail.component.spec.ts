import { HttpClientModule } from '@angular/common/http';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ReactiveFormsModule } from '@angular/forms';
import {
  MatSnackBarModule,
  MatSnackBar,
  MatSnackBarRef,
} from '@angular/material/snack-bar';
import { RouterTestingModule } from '@angular/router/testing';
import { expect } from '@jest/globals';
import { SessionService } from '../../../../services/session.service';

import { DetailComponent } from './detail.component';
import {
  HttpClientTestingModule,
  HttpTestingController,
} from '@angular/common/http/testing';
import { Router } from '@angular/router';
import { SessionApiService } from '../../services/session-api.service';
import { of } from 'rxjs';

describe('DetailComponent', () => {
  let component: DetailComponent;
  let fixture: ComponentFixture<DetailComponent>;
  let service: SessionService;
  let serviceApi: SessionApiService;
  let router: Router;
  let httpTestingController: HttpTestingController;
  let matSnackBar: MatSnackBar;

  const mockSessionService = {
    sessionInformation: {
      admin: true,
      id: 1,
    },
  };

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [
        RouterTestingModule,
        HttpClientModule,
        HttpClientTestingModule,
        MatSnackBarModule,
        ReactiveFormsModule,
      ],
      declarations: [DetailComponent],
      providers: [
        { provide: SessionService, useValue: mockSessionService },
        SessionApiService,
      ],
    }).compileComponents();
    service = TestBed.inject(SessionService);
    serviceApi = TestBed.inject(SessionApiService);
    fixture = TestBed.createComponent(DetailComponent);
    router = TestBed.inject(Router);
    httpTestingController = TestBed.inject(HttpTestingController);
    matSnackBar = TestBed.inject(MatSnackBar);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  // Test pour vérifier que ngOnInit appelle la méthode fetchSession
  it('should call ngOnInit', () => {
    const mockPrivateFetchSession = jest.spyOn(
      component as any,
      'fetchSession'
    );
    component.ngOnInit();
    expect(mockPrivateFetchSession).toBeCalled();
  });

  // Test pour vérifier que la méthode back appelle window.history.back
  it('should call back', () => {
    jest.spyOn(window.history, 'back');
    component.back();
    expect(window.history.back).toBeCalled();
  });

  it('should call delete and open MatSnackBar', () => {
    // Given
    const sessionId = '1';
    const mockApiResponse = null;
    const deleteSpy = jest
      .spyOn(serviceApi, 'delete')
      .mockReturnValue(of(mockApiResponse));
    const snackBarOpenSpy = jest.spyOn(matSnackBar, 'open');

    // When
    component.sessionId = sessionId;
    component.delete();

    // Then
    expect(deleteSpy).toHaveBeenCalledWith(sessionId);
    expect(snackBarOpenSpy).toHaveBeenCalledWith('Session deleted !', 'Close', {
      duration: 3000,
    });
  });

  it('should call participate on sessionApiService and fetchSession after participation', () => {
    // Given
    const sessionId = '1';
    const userId = '1';
    const mockApiResponse = null;
    const participateSpy = jest
      .spyOn(serviceApi, 'participate')
      .mockReturnValue(of(mockApiResponse));
    const fetchSessionSpy = jest.spyOn(component as any, 'fetchSession');

    // When
    component.sessionId = sessionId;
    component.userId = userId;
    component.participate();

    // Then
    expect(participateSpy).toHaveBeenCalledWith(sessionId, userId);
    expect(fetchSessionSpy).toHaveBeenCalled();
  });
});
