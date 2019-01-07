package pesa.db;

import pesa.db.model.DiscussionEntity;
import pesa.db.model.UserRoleEntity;
import pesa.db.model.WebUserEntity;

import javax.persistence.*;
import java.util.List;

public class DBO {

    private static final String PU_NAME = "WebDBPU";
    private static EntityManagerFactory emf = null;
    private static final int regularRole = 2;

    public static List<WebUserEntity> getRegisteredUsers() {
        List<WebUserEntity> ret;

        EntityManager em = getEntityManager();

        TypedQuery<WebUserEntity> query = em.createQuery("select x from WebUserEntity x order by x.registrationDate", WebUserEntity.class);

        ret = query.getResultList();

        em.close();

        return ret;
    }

    public static boolean addNewUser(WebUserEntity user) {
        EntityManager em = getEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.persist(user);
        em.flush();

        UserRoleEntity role = new UserRoleEntity();
        role.setUserId(user.getUserId());
        role.setRoleId(regularRole);
        em.persist(role);
        em.flush();
        tx.commit();

        em.close();

        return true;
    }

    public static int getUserRole(int userId) {
        int ret;

        EntityManager em = getEntityManager();

        TypedQuery<Integer> query = em.createQuery("select x.roleId from UserRoleEntity x where x.userId=" + userId, Integer.class);

        ret = query.getSingleResult();

        em.close();

        return ret;
    }

    public static WebUserEntity getUser(String nickname) {
        WebUserEntity ret;

        EntityManager em = getEntityManager();

        TypedQuery<WebUserEntity> query = em.createQuery("select x from WebUserEntity x where x.nickname='" + nickname + "'", WebUserEntity.class);

        ret = query.getSingleResult();

        em.close();

        return ret;
    }

    public static WebUserEntity getUser(int userId) {
        WebUserEntity ret;

        EntityManager em = getEntityManager();

        TypedQuery<WebUserEntity> query = em.createQuery("select x from WebUserEntity x where x.userId=" + userId, WebUserEntity.class);

        ret = query.getSingleResult();

        em.close();

        return ret;
    }

    public static boolean changeUsername(WebUserEntity user, String newUsername) {
        EntityManager em = getEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        em.getReference(WebUserEntity.class, user.getUserId()).setNickname(newUsername);
        em.flush();

        tx.commit();
        em.close();

        return true;
    }

    public static boolean changePassword(WebUserEntity user, String newPassword) {

        EntityManager em = getEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        em.getReference(WebUserEntity.class, user.getUserId()).setPassword(newPassword);
        em.flush();

        tx.commit();
        em.close();

        return true;
    }

    public static boolean addNewComment(DiscussionEntity comment) {

        EntityManager em = getEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        em.persist(comment);
        em.flush();

        tx.commit();
        em.close();

        return true;
    }

    public static boolean deleteComment(int commentId) {

        EntityManager em = getEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        DiscussionEntity comm = em.getReference(DiscussionEntity.class, commentId);
        em.remove(comm);
        em.flush();

        tx.commit();
        em.close();

        return true;
    }

    public static List<DiscussionEntity> getComments() {
        List<DiscussionEntity> ret;

        EntityManager em = getEntityManager();

        TypedQuery<DiscussionEntity> query = em.createQuery("select x from DiscussionEntity x order by x.additionDate desc", DiscussionEntity.class);

        ret = query.getResultList();

        em.close();

        return ret;
    }

    private static EntityManager getEntityManager() {
        if (emf == null) {
            initEntityManagerFactory();
        }
        return emf.createEntityManager();
    }

    private static void initEntityManagerFactory() {
        if (emf != null) {
            return;
        }
        emf = Persistence.createEntityManagerFactory(PU_NAME);
    }
}
