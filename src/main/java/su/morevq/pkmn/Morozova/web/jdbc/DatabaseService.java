package su.morevq.pkmn.Morozova.web.jdbc;

import su.morevq.pkmn.models.card.Card;
import su.morevq.pkmn.models.student.Student;

import java.sql.SQLException;

public interface DatabaseService {

    Card getCardFromDatabase(String cardName) throws SQLException;

    Student getStudentFromDatabase(String studentName);

    void saveCardToDatabase(Card card) throws SQLException;

    void createPokemonOwner(Student student) throws SQLException;
}
