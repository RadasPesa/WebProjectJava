package pesa.app;

import pesa.db.DBO;
import pesa.db.model.DiscussionEntity;

import java.util.List;

public class CommentBO {

    public static List<DiscussionEntity> getComments() {
        return DBO.getComments();
    }

    public static boolean addNewComment(DiscussionEntity comment) {
        return DBO.addNewComment(comment);
    }

    public static boolean deleteComment(int commentId) {
        return DBO.deleteComment(commentId);
    }
}
