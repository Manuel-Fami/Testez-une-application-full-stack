import { HttpClientModule } from '@angular/common/http';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import {
  FormBuilder,
  FormGroup,
  FormsModule,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import {
  BrowserAnimationsModule,
  NoopAnimationsModule,
} from '@angular/platform-browser/animations';
import { RouterTestingModule } from '@angular/router/testing';
import { expect } from '@jest/globals';
import { SessionService } from 'src/app/services/session.service';
import { SessionApiService } from '../../services/session-api.service';

import { FormComponent } from './form.component';
import { ActivatedRoute, Router } from '@angular/router';
import { of } from 'rxjs';
import { Session } from '../../interfaces/session.interface';
import {
  HttpClientTestingModule,
  HttpTestingController,
} from '@angular/common/http/testing';
import { NgZone } from '@angular/core';

describe('FormComponent', () => {
  let component: FormComponent;
  let fixture: ComponentFixture<FormComponent>;
  let sessionService: SessionService;
  let sessionApiService: SessionApiService;
  let ngZone: NgZone;
  let router: Router;
  let route: ActivatedRoute;

  const mockSessionService = {
    sessionInformation: {
      admin: true,
    },
  };

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [
        RouterTestingModule.withRoutes([
          { path: 'sessions', component: FormComponent },
        ]),
        HttpClientModule,
        MatCardModule,
        MatIconModule,
        MatFormFieldModule,
        MatInputModule,
        ReactiveFormsModule,
        MatSnackBarModule,
        MatSelectModule,
        BrowserAnimationsModule,
        HttpClientTestingModule,
        RouterTestingModule,
        NoopAnimationsModule,
      ],
      providers: [
        { provide: SessionService, useValue: mockSessionService },
        SessionApiService,
      ],
      declarations: [FormComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(FormComponent);
    sessionService = TestBed.inject(SessionService);
    sessionApiService = TestBed.inject(SessionApiService);
    router = TestBed.inject(Router);
    route = TestBed.inject(ActivatedRoute);
    ngZone = TestBed.inject(NgZone);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  // Suite de tests pour la méthode ngOnInit
  describe('ngOnInit', () => {
    it('should initialize form when URL includes "update"', () => {
      // Simulate URL with "update"
      jest.spyOn(router, 'url', 'get').mockReturnValueOnce('/update/123');

      const paramMapSpy = jest
        .spyOn(component.route.snapshot.paramMap, 'get')
        .mockReturnValueOnce('123');

      const session: Session = {
        id: 1,
        name: 'toto',
        description: 'test',
        date: new Date(),
        teacher_id: 1,
        users: [1, 2, 3],
        createdAt: new Date(),
        updatedAt: new Date(),
      };

      jest.spyOn(sessionApiService, 'detail').mockReturnValueOnce(of(session));
      const initFormSpy = jest.spyOn(component, 'initForm');

      component.ngOnInit();

      expect(component.onUpdate).toBe(true);
      expect(paramMapSpy).toHaveBeenCalledWith('id');
      expect(component.id).toEqual('123');
      expect(sessionApiService.detail).toHaveBeenCalledWith('123');
      expect(initFormSpy).toHaveBeenCalledWith(session);
    });

    it('should initialize form when URL does not include "update"', () => {
      jest.spyOn(router, 'url', 'get').mockReturnValueOnce('/sessions');
      const initFormSpy = jest.spyOn(component, 'initForm');

      component.ngOnInit();

      expect(component.onUpdate).toBe(false);
      expect(component.id).toBeUndefined();
      expect(initFormSpy).toHaveBeenCalled();
    });
  });

  describe('submit', () => {
    it('should call create method when onUpdate is false', () => {
      // Given
      component.onUpdate = false;

      // Mocker sessionApiService.create pour retourner un observable immédiat
      const createSpy = jest
        .spyOn(sessionApiService, 'create')
        .mockReturnValue(of({} as any));
      const exitPageSpy = jest.spyOn(component as any, 'exitPage');

      // When
      component.submit();

      // Then
      expect(createSpy).toHaveBeenCalled();
      expect(exitPageSpy).toHaveBeenCalledWith('Session created !');
    });

    it('should call update method when onUpdate is true', () => {
      // Given
      component.onUpdate = true;

      // Mocker sessionApiService.create pour retourner un observable immédiat
      const updateSpy = jest
        .spyOn(sessionApiService, 'update')
        .mockReturnValue(of({} as any));
      const exitPageSpy = jest.spyOn(component as any, 'exitPage');

      // When
      component.submit();

      // Then
      expect(updateSpy).toHaveBeenCalled();
      expect(exitPageSpy).toHaveBeenCalledWith('Session updated !');
    });
  });
});
