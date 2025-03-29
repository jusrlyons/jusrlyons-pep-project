package Controller;

import java.util.ArrayList;
import java.util.List;

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

    public Javalin startAPI() {
        Javalin app = Javalin.create();

        // Account Endpoints
        app.post("/accounts", this::createAccount);
        app.get("/accounts/{id}", this::getAccountById);
        app.get("/accounts", this::getAllAccounts);
        app.put("/accounts/{id}", this::updateAccount);
        app.delete("/accounts/{id}", this::deleteAccount);

        // Message Endpoints
        app.post("/messages", this::createMessage);
        app.get("/messages/{id}", this::getMessageById);
        app.get("/messages", this::getAllMessages);
        app.get("/messages/user/{userId}", this::getMessagesByUserId);
        app.delete("/messages/{id}", this::deleteMessage);

        return app;
    }

    // Account Handlers
    private void createAccount(Context context) {
        Account account = context.bodyAsClass(Account.class);
        Account createdAccount = accountService.createAccount(account.getUsername(), account.getPassword());
        if (createdAccount != null) {
            context.status(201).json(createdAccount);
        } else {
            context.status(400).json("Error creating account");
        }
    }

    private void getAccountById(Context context) {
        int accountId = Integer.parseInt(context.pathParam("id"));
        Account account = accountService.getAccountById(accountId);
        if (account != null) {
            context.status(200).json(account);
        } else {
            context.status(404).json("Account not found");
        }
    }

    private void getAllAccounts(Context context) {
        context.status(200).json(accountService.getAllAccounts());
    }

    private void updateAccount(Context context) {
        int accountId = Integer.parseInt(context.pathParam("id"));
        Account account = context.bodyAsClass(Account.class);
        Account updatedAccount = accountService.updateAccount(accountId, account.getUsername(), account.getPassword());
        if (updatedAccount != null) {
            context.status(200).json(updatedAccount);
        } else {
            context.status(400).json("Error updating account");
        }
    }

    private void deleteAccount(Context context) {
        int accountId = Integer.parseInt(context.pathParam("id"));
        boolean deleted = accountService.deleteAccount(accountId);
        if (deleted) {
            context.status(204);
        } else {
            context.status(404).json("Account not found");
        }
    }

    // Message Handlers
    private void createMessage(Context context) {
        Message message = context.bodyAsClass(Message.class);
        Message createdMessage = messageService.createMessage(message.getPosted_by(), message.getMessage_text(), message.getTime_posted_epoch());
        if (createdMessage != null) {
            context.status(201).json(createdMessage);
        } else {
            context.status(400).json("Error creating message");
        }
    }

    private void getMessageById(Context context) {
        int userId = Integer.parseInt(context.pathParam("userId"));
        List<Message> messages = messageService.getMessagesByUserId(userId);
        if (messages.isEmpty()) {
            context.status(200).json(new ArrayList<>());  // Return empty array if no messages
        } else {
        context.status(200).json(messages);  // Return the messages
        }
    }

    private void getAllMessages(Context context) {
        context.status(200).json(messageService.getAllMessages());
    }

    private void getMessagesByUserId(Context context) {
        int userId = Integer.parseInt(context.pathParam("userId"));
        context.status(200).json(messageService.getMessagesByUserId(userId));
    }

    private void deleteMessage(Context context) {
        int messageId = Integer.parseInt(context.pathParam("id"));
        boolean deleted = messageService.deleteMessage(messageId);
        if (deleted) {
            context.status(204);
        } else {
            context.status(404).json("Message not found");
        }
    }
}