/// <reference types="cypress" />

describe('Login spec', () => {
  const user = {
    id: 1,
    firstName: 'Admin',
    lastName: 'Admin',
    email: 'yoga@studio.com',
    password: 'test!1234',
    admin: true,
  };

  beforeEach(() => {
    cy.visit('/login');
  });

  it('Login successfull', () => {
    cy.intercept('POST', '/api/auth/login', {
      body: user,
    });

    cy.intercept(
      {
        method: 'GET',
        url: '/api/session',
      },
      []
    ).as('session');

    cy.get('input[formControlName=email]').type('yoga@studio.com');
    cy.get('input[formControlName=password]').type(
      `${'test!1234'}{enter}{enter}`
    );

    cy.url().should('eq', `${Cypress.config().baseUrl}sessions`);

    cy.get('.error').should('not.exist');

    cy.intercept('GET', `/api/user/${user.id}`, {
      statusCode: 200,
      body: user,
    }).as('getUser');

    cy.get('span[routerLink=me]').click();
    cy.url().should('include', '/me');
    cy.get('p.my2').should('contain', 'You are admin');
  });

  it('should logout successfully', () => {
    cy.intercept('POST', '/api/auth/login', {
      statusCode: 201,
      body: user,
    });

    cy.intercept('GET', '/api/session', {
      statusCode: 200,
      body: [],
    }).as('session');

    cy.get('input[formControlName=email]').type(user.email);
    cy.get('input[formControlName=password]').type(
      `${user.password}{enter}{enter}`
    );

    cy.get('.link').contains('Logout').click();

    cy.url().should('eq', Cypress.config().baseUrl);
    cy.get('.error').should('not.exist');
  });

  it('should not login with incorrect email', () => {
    cy.intercept('POST', '/api/auth/login', {
      statusCode: 401,
      body: { message: 'Invalid email or password' },
    });

    cy.get('input[formControlName=email]').type('bademail');
    cy.get('input[formControlName=password]').type(
      `${user.password}{enter}{enter}`
    );

    cy.get('.error').should('contain', 'An error occurred');
  });

  it('should have the submit button disabled when fields are empty', () => {
    cy.get('input[formControlName=email]').clear();
    cy.get('input[formControlName=password]').clear();

    cy.get('button[type=submit]').should('be.disabled');
  });

  it('should not login with incorrect password', () => {
    cy.intercept('POST', '/api/auth/login', {
      statusCode: 401,
      body: { message: 'Invalid email or password' },
    });

    cy.get('input[formControlName=email]').type(user.email);
    cy.get('input[formControlName=password]').type(
      `wrongpassword{enter}{enter}`
    );

    cy.get('.error').should('contain', 'An error occurred');
  });

  it('should toggle password visibility', () => {
    // Le mot de passe doit être caché par défaut
    cy.get('input[formControlName=password]').should(
      'have.attr',
      'type',
      'password'
    );

    // Clique sur le bouton pour afficher le mot de passe
    cy.get('button[aria-label="Hide password"]').click();
    cy.get('input[formControlName=password]').should(
      'have.attr',
      'type',
      'text'
    );

    // Clique à nouveau pour masquer le mot de passe
    cy.get('button[aria-label="Hide password"]').click();
    cy.get('input[formControlName=password]').should(
      'have.attr',
      'type',
      'password'
    );
  });

  it('should disable submit button when form is invalid', () => {
    // Vérifie que le bouton est désactivé au départ
    cy.get('button[type=submit]').should('be.disabled');

    // Remplit un champ partiellement valide et vérifie que le bouton est toujours désactivé
    cy.get('input[formControlName=email]').type('yoga@studio');
    cy.get('button[type=submit]').should('be.disabled');

    // Termine de remplir les champs correctement et vérifie que le bouton est activé
    cy.get('input[formControlName=email]').clear().type('yoga@studio.com');
    cy.get('input[formControlName=password]').type('test!1234');
    cy.get('button[type=submit]').should('not.be.disabled');
  });
});
