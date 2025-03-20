package Controller;

import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;
import io.javalin.Javalin;
import io.javalin.http.Context;

public class SocialMediaController {

    private final AccountService accountService;
    private final MessageService messageService;

    public SocialMediaController() {
        this.accountService = new AccountService(); // Instantiate AccountService
        this.messageService = new MessageService(); // Instantiate MessageService
    }

    /**
     * This method defines the API endpoints and handlers.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();

        // Account Endpoints
        app.post("/accounts", this::createAccount);   // Create a new account
        app.get("/accounts/{id}", this::getAccountById); // Get account by ID
        app.get("/accounts", this::getAllAccounts);    // Get all accounts
        app.put("/accounts/{id}", this::updateAccount); // Update account
        app.delete("/accounts/{id}", this::deleteAccount); // Delete account

        // Message Endpoints
        app.post("/messages", this::createMessage);    // Create a new message
        app.get("/messages/{id}", this::getMessageById); // Get message by ID
        app.get("/messages", this::getAllMessages);    // Get all messages
        app.get("/messages/user/{userId}", this::getMessagesByUserId); // Get messages by user ID
        app.delete("/messages/{id}", this::deleteMessage); // Delete message

        return app;
    }

    // Account Handlers

    // Create an account
    private void createAccount(Context context) {
        Account account = context.bodyAsClass(Account.class);
        Account createdAccount = accountService.createAccount(account);
        if (createdAccount != null) {
            context.status(201).json(createdAccount); // Respond with created account and status 201
        } else {
            context.status(400).json("Error creating account"); // Error if creation fails
        }
    }

    // Get account by ID
    private void getAccountById(Context context) {
        int accountId = Integer.parseInt(context.pathParam("id"));
        Account account = accountService.getAccountById(accountId);
        if (account != null) {
            context.status(200).json(account); // Return account details
        } else {
            context.status(404).json("Account not found"); // Return error if not found
        }
    }

    // Get all accounts
    private void getAllAccounts(Context context) {
        context.status(200).json(accountService.getAllAccounts()); // Return all accounts
    }

    // Update an account
    private void updateAccount(Context context) {
        int accountId = Integer.parseInt(context.pathParam("id"));
        Account account = context.bodyAsClass(Account.class);
        account.setAccount_id(accountId); // Set account ID from path param
        Account updatedAccount = accountService.updateAccount(account);
        if (updatedAccount != null) {
            context.status(200).json(updatedAccount); // Return updated account
        } else {
            context.status(400).json("Error updating account"); // Error if update fails
        }
    }

    // Delete an account
    private void deleteAccount(Context context) {
        int accountId = Integer.parseInt(context.pathParam("id"));
        boolean deleted = accountService.deleteAccount(accountId);
        if (deleted) {
            context.status(204); // Return status 204 for successful deletion
        } else {
            context.status(404).json("Account not found"); // Error if account not found
        }
    }

    // Message Handlers

    // Create a new message
    private void createMessage(Context context) {
        Message message = context.bodyAsClass(Message.class);
        Message createdMessage = messageService.createMessage(message);
        if (createdMessage != null) {
            context.status(201).json(createdMessage); // Respond with created message and status 201
        } else {
            context.status(400).json("Error creating message"); // Error if creation fails
        }
    }

    // Get a message by ID
    private void getMessageById(Context context) {
        int messageId = Integer.parseInt(context.pathParam("id"));
        Message message = messageService.getMessageById(messageId);
        if (message != null) {
            context.status(200).json(message); // Return message details
        } else {
            context.status(404).json("Message not found"); // Return error if not found
        }
    }

    // Get all messages
    private void getAllMessages(Context context) {
        context.status(200).json(messageService.getAllMessages()); // Return all messages
    }

    // Get messages by user ID
    private void getMessagesByUserId(Context context) {
        int userId = Integer.parseInt(context.pathParam("userId"));
        context.status(200).json(messageService.getMessagesByUserId(userId)); // Return messages by user
    }

    // Delete a message
    private void deleteMessage(Context context) {
        int messageId = Integer.parseInt(context.pathParam("id"));
        boolean deleted = messageService.deleteMessage(messageId);
        if (deleted) {
            context.status(204); // Return status 204 for successful deletion
        } else {
            context.status(404).json("Message not found"); // Error if message not found
        }
    }

}