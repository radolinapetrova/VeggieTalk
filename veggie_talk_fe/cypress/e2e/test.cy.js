describe('Login Page', () => {
    it('should successfully log in with correct credentials', () => {

        cy.visit('http://localhost:3000/login');

        cy.get('input[id="username"]').type('admin');

        cy.get('input[id="password"]').type('admin');

        cy.get('input[id="email"]').type('admin');

    });


});