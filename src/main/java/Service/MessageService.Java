package Service;

import Model.Message;
import DAO.MessageDAO;

import java.util.List;

public class MessageService {

    private MessageDAO messageDAO;

    public MessageService() {
        this.messageDAO = new MessageDAO(); // Assuming MessageDAO handles DB interactions
    }

    // Create a new message
    public Message createMessage(int postedBy, String messageText, long timePostedEpoch) {
        // You can add business logic to validate or manipulate message data before saving
        Message newMessage = new Message(postedBy, messageText, timePostedEpoch);
        return messageDAO.createMessage(newMessage);
    }

    // Get message by ID
    public Message getMessageById(int messageId) {
        return messageDAO.getMessageById(messageId);
    }

    // Get all messages for a specific user
    public List<Message> getMessagesByUserId(int userId) {
        return messageDAO.getMessagesByUserId(userId);
    }

    // Get all messages
    public List<Message> getAllMessages() {
        return messageDAO.getAllMessages();
    }

    // Delete a message by ID
    public boolean deleteMessage(int messageId) {
        return messageDAO.deleteMessage(messageId);
    }
}