package pesa.db.model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "DISCUSSION", schema = "SA")
public class DiscussionEntity {
    private int commentId;
    private int userId;
    private String comment;
    private Timestamp additionDate;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "COMMENT_ID")
    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    @Basic
    @Column(name = "USER_ID")
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "COMMENT")
    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Basic
    @Column(name = "ADDITION_DATE")
    public Timestamp getAdditionDate() {
        return additionDate;
    }

    public void setAdditionDate(Timestamp additionDate) {
        this.additionDate = additionDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DiscussionEntity that = (DiscussionEntity) o;
        return commentId == that.commentId &&
                userId == that.userId &&
                Objects.equals(comment, that.comment) &&
                Objects.equals(additionDate, that.additionDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(commentId, userId, comment, additionDate);
    }
}
