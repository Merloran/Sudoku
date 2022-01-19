/**
 * MIT License
 * Copyright (c) 2021 Maciej Wójcik, Maciej Poncyleusz
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package pl.comp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class JdbcSudokuBoardDao implements Dao<SudokuBoard> {
    private Connection connection;
    private Statement statement;
    private ResultSet result;
    private String name;

    public JdbcSudokuBoardDao(String name) {
        this.name = name;
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Sudoku",
                    "root", "");
            statement = connection.createStatement();

            statement.execute("CREATE TABLE IF NOT EXISTS board"
                                + "(board_id INTEGER NOT NULL AUTO_INCREMENT,"
                                + " name VARCHAR(50),"
                                + " PRIMARY KEY(board_id))");

            statement.execute("CREATE TABLE IF NOT EXISTS field"
                                + "(x_coord INTEGER,"
                                + " y_coord INTEGER,"
                                + " value INTEGER,"
                                + " board_id INTEGER NOT NULL,"
                                + " FOREIGN KEY(board_id) REFERENCES board(board_id))");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public SudokuBoard read() throws ReadFileException {
        try {
            result = statement.executeQuery("SELECT * "
                    + "FROM field JOIN board ON field.board_id = board.board_id "
                    + "WHERE board.name LIKE '" + name + "'");
            SudokuBoard b = new SudokuBoard(new BacktrackingSudokuSolver());

            while (result.next()) {
                b.set(result.getInt(1), result.getInt(2), result.getInt(3), false);
            }
            return b;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void write(SudokuBoard board) throws WriteFileException {
        try {
            statement.execute("INSERT INTO board"
                                + "(name)"
                                + "VALUES"
                                + "('" + name + "');");

            result = statement.executeQuery("SELECT board_id "
                                            + "FROM board WHERE name LIKE '"
                                            + name
                                            + "'");
            StringBuilder text = new StringBuilder();
            String id = new String();
            while (result.next()) {
                id = String.valueOf(result.getInt("board_id"));
            }
            for (int x = 0; x < 9; x++) {
                for (int y = 0; y < 9; y++) {
                    text.append("(").append(x).append(",").append(y).append(",")
                        .append(board.get(x, y)).append(",")
                        .append(id).append(")");
                    if (x != 8 || y != 8) {
                        text.append(",\n");
                    }
                }
            }
            statement.execute("INSERT INTO field "
                                + "(x_coord, y_coord, value, board_id)"
                                + " VALUES "
                                + text);
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    public ArrayList<String> readAll() throws SQLException {
        result = statement.executeQuery("SELECT name "
                                          + "FROM board;");
        ArrayList<String> list = new ArrayList<>();
        while (result.next()) {
            list.add(result.getString(1));
        }
        return list;
    }

    public void delete() throws SQLException {
        statement.execute("DROP TABLE field;");
        statement.execute("DROP TABLE board;");
    }

    @Override
    public void close() throws Exception {
        result.close();
        statement.close();
        connection.close();
    }
}
