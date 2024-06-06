describe("Registration Form", () => {
    beforeEach(() => {
        cy.visit("http://localhost:3000/login"); // Assuming your app is running on localhost:3000
    });

    it("should display error message when not agreeing to terms", () => {
        cy.get("input[name='email']").type("test@example.com");
        cy.get("input[name='password']").type("password");
        cy.get("button[name='registerButton']").click();
        cy.get(".message").should("contain", "You must agree to the terms of GDPR to register.");
    });

    it("should redirect to homepage after successful registration", () => {
        cy.get("input[name='email']").type("test@example.com");
        cy.get("input[name='password']").type("password");
        cy.get("input[type='checkbox']").check();
        cy.get("button[name='registerButton']").click();
        cy.get(".modal").should("be.visible"); // Modal indicating successful registration
        cy.wait(3000); // Wait for modal to disappear
        cy.url().should("eq", "http://localhost:3000/"); // Check if redirected to homepage
    });
});