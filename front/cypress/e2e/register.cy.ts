/// <reference types="cypress" />

describe('register spec', () => {
  const newUser = {
    id: 1,
    firstName: 'new',
    lastName: 'user',
    email: 'newUser@email.com',
    password: 'password',
  };

  beforeEach(() => {
    cy.visit('/register');
  });

  it('should display an error if the required email field is not filled', () => {
    // When
    cy.get('input[formControlName=firstName]').type(newUser.firstName);
    cy.get('input[formControlName=lastName]').type(newUser.lastName);
    cy.get('input[formControlName="email"]').type(`{enter}`);
    cy.get('input[formControlName=password]').type(newUser.password);

    // Then
    cy.get('input[formControlName="email"]').should('have.class', 'ng-invalid');
    cy.get('button[type=submit]').should('be.disabled');
  });

  // ----------------------------------------------------------------
  it('should display an error if the required first name field is not filled', () => {
    // When
    cy.get('input[formControlName=lastName]').type(newUser.lastName);
    cy.get('input[formControlName=firstName]').type(`{enter}`);
    cy.get('input[formControlName="email"]').type(newUser.email);
    cy.get('input[formControlName=password]').type(newUser.password);

    // Then
    cy.get('input[formControlName="firstName"]').should(
      'have.class',
      'ng-invalid'
    );
    cy.get('button[type=submit]').should('be.disabled');
  });

  it('should display an error if the required last name field is not filled', () => {
    // When
    cy.get('input[formControlName=lastName]').type(`{enter}`);
    cy.get('input[formControlName=firstName]').type(newUser.firstName);
    cy.get('input[formControlName="email"]').type(newUser.email);
    cy.get('input[formControlName=password]').type(newUser.password);

    // Then
    cy.get('input[formControlName="lastName"]').should(
      'have.class',
      'ng-invalid'
    );
    cy.get('button[type=submit]').should('be.disabled');
  });

  it('should display an error if the register service returns an error', () => {
    // Given
    cy.intercept('POST', '/api/auth/register', {
      statusCode: 500,
      body: { error: 'An error occurred' },
    }).as('register');

    // When
    // Remplissage du formulaire d'inscription et soumission
    cy.get('input[formControlName=firstName]').type(newUser.firstName);
    cy.get('input[formControlName=lastName]').type(newUser.lastName);
    cy.get('input[formControlName="email"]').type(newUser.email);
    cy.get('input[formControlName=password]').type(newUser.password);
    cy.get('button[type=submit]').click();

    // Then
    cy.get('span.error.ml2.ng-star-inserted').should(
      'contain.text',
      'An error occurred'
    );
  });

  it('should display an error if the required password field is to short', () => {
    // When
    cy.get('input[formControlName=firstName]').type(newUser.firstName);
    cy.get('input[formControlName=lastName]').type(newUser.lastName);
    cy.get('input[formControlName="email"]').type(newUser.email);
    cy.get('input[formControlName=password]').type('ab');
    cy.get('button[type=submit]').click();

    // Then
    cy.get('span.error.ml2.ng-star-inserted').should(
      'contain.text',
      'An error occurred'
    );
  });

  it('should register, log in, verify user account details and delete it', () => {
    /* 1. Inscription de l'utilisateur */
    // Given
    cy.intercept('POST', '/api/auth/register', {
      statusCode: 201,
      body: newUser,
    }).as('register');

    // When
    cy.get('input[formControlName=firstName]').type(newUser.firstName);
    cy.get('input[formControlName=lastName]').type(newUser.lastName);
    cy.get('input[formControlName=email]').type(newUser.email);
    cy.get('input[formControlName=password]').type(newUser.password);
    cy.get('button[type=submit]').click();

    // Then
    cy.url().should('include', '/login');

    /* 2. Connexion de l'utilisateur */
    // Given
    cy.intercept('POST', '/api/auth/login', {
      statusCode: 201,
      body: newUser,
    }).as('login');

    cy.intercept('GET', '/api/session', {
      statusCode: 200,
      body: newUser,
    }).as('getSession');

    // When
    cy.get('input[formControlName=email]').type(newUser.email);
    cy.get('input[formControlName=password]').type(
      `${newUser.password}{enter}{enter}`
    );

    // Interception de la requête de l'utilisateur et simulation d'une réponse réussie
    cy.intercept('GET', `/api/user/${newUser.id}`, {
      statusCode: 200,
      body: newUser,
    }).as('getUser');

    // Clic sur le lien vers la page de l'utilisateur
    cy.get('span[routerLink=me]').click();

    // Then
    cy.url().should('include', '/me');

    /* 3. Affichage des détails de l'utilisateur */
    // Vérification que les détails de l'utilisateur sont corrects
    cy.get('.m3 mat-card-content p')
      .contains(`Name: ${newUser.firstName} ${newUser.lastName.toUpperCase()}`)
      .should('exist');
    cy.get('.m3 mat-card-content p')
      .contains(`Email: ${newUser.email}`)
      .should('exist');
    cy.get('.m3 mat-card-content div.my2').should('exist');

    // Given
    cy.intercept('DELETE', `/api/user/${newUser.id}`, {
      statusCode: 200,
    }).as('deleteUser');

    /* 4. Suppression de l'utilisateur */
    // When
    cy.get('.my2 > .mat-focus-indicator').click();

    // Then
    cy.wait('@deleteUser').its('response.statusCode').should('eq', 200);

    // Vérification que l'utilisateur est redirigé vers la page de connexion après la suppression
    cy.url().should('eq', 'http://localhost:4200/');
  });
});
