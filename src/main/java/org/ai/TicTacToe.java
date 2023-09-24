package org.ai;
/*
  @author emilia
  @project AI
  @class TicTacToe
  @version 1.0.0
  @since 23.09.2023 - 20:12
*/
import java.util.Scanner;

public class TicTacToe {
    private static final char[][] board = new char[3][3];
    private static char currentPlayer = 'X';
    private static final char computerPlayer = 'O';

    public static void main(String[] args) {
        initializeBoard();
        printBoard();

        Scanner scanner = new Scanner(System.in);
        int row, col;

        while (true) {
            if (currentPlayer == 'X') {
                System.out.println("Player " + currentPlayer + ", enter row (0, 1, 2) and column (0, 1, 2): ");
                row = scanner.nextInt();
                col = scanner.nextInt();
            } else {
                // Хід комп'ютера
                int[] computerMove = minimax();
                row = computerMove[0];
                col = computerMove[1];
                System.out.println("The computer selected a line " + row + " and a column " + col);
            }

            if (isValidMove(row, col)) {
                makeMove(row, col);
                printBoard();

                if (checkWin()) {
                    System.out.println("Player " + currentPlayer + " won!");
                    break;
                } else if (isBoardFull()) {
                    System.out.println("A draw!");
                    break;
                }

                // Переключення до наступного гравця
                currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
            } else {
                System.out.println("Wrong move. Try again.");
            }
        }
    }

    /**
     * Ініціалізує дошку гри, заповнюючи всі клітинки пустими символами ' '.
     * Початковий стан гри перед початком гри.
     */
    private static void initializeBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = ' ';
            }
        }
    }

    private static void printBoard() {
        System.out.println("-------------");
        for (int i = 0; i < 3; i++) {
            System.out.print("| ");
            for (int j = 0; j < 3; j++) {
                System.out.print(board[i][j] + " | ");
            }
            System.out.println("\n-------------");
        }
    }

    /**
     * Перевіряє, чи є вказана клітинка (рядок та стовпчик) на дошці доступною для ходу.
     *
     * @param row Рядок клітинки, яку потрібно перевірити.
     * @param col Стовпчик клітинки, яку потрібно перевірити.
     * @return true, якщо клітинка вільна (пуста) і належить до діапазону 3x3 дошки; в іншому випадку, false.
     */
    private static boolean isValidMove(int row, int col) {
        return (row >= 0 && row < 3 && col >= 0 && col < 3 && board[row][col] == ' ');
    }

    /**
     * Перевіряє, чи є вказана клітинка (рядок та стовпчик) на дошці доступною для ходу.
     *
     * @param row Рядок клітинки, яку потрібно перевірити.
     * @param col Стовпчик клітинки, яку потрібно перевірити.
     * @return true, якщо клітинка вільна (пуста) і належить до діапазону 3x3 дошки; в іншому випадку, false.
     */
    private static void makeMove(int row, int col) {
        board[row][col] = currentPlayer;
    }


    /**
     * Перевіряє, чи поточний гравець (currentPlayer) виграв гру шляхом перевірки горизонталей, вертикалей і діагоналей на дошці гри.
     *
     * @return true, якщо поточний гравець переміг, в іншому випадку, false.
     */
    private static boolean checkWin() {
        // Перевірка горизонталей, вертикалей
        for (int i = 0; i < 3; i++) {
            if (board[i][0] == currentPlayer && board[i][1] == currentPlayer && board[i][2] == currentPlayer)
                return true;
            if (board[0][i] == currentPlayer && board[1][i] == currentPlayer && board[2][i] == currentPlayer)
                return true;
        }

        // Перевірка діагоналей
        if (board[0][0] == currentPlayer && board[1][1] == currentPlayer && board[2][2] == currentPlayer)
            return true;
        return board[0][2] == currentPlayer && board[1][1] == currentPlayer && board[2][0] == currentPlayer; // Гравець не переміг

    }

    /**
     * Перевіряє, чи дошка гри заповнена, тобто чи не залишилося вільних клітинок для ходу.
     *
     * @return true, якщо дошка повністю заповнена символами 'X' та 'O'; в іншому випадку, false.
     */
    private static boolean isBoardFull() {
        // Перебираємо всі клітинки на дошці
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                // Якщо знайдено хоча б одну вільну клітинку (пробіл), то дошка не є повністю заповненою
                if (board[i][j] == ' ')
                    return false;
            }
        }
        // Якщо не знайдено вільних клітинок, то дошка повністю заповнена
        return true;
    }

    /**
     * Використовує алгоритм Мінімакс для обчислення найкращого ходу для комп'ютера (гравця 'O').
     * Метод перебирає всі можливі ходи, оцінює їх і повертає найкращий хід для комп'ютера.
     *
     * @return Масив із двома значеннями, де перше значення - рядок, а друге - стовпчик, який представляє найкращий хід для комп'ютера.
     */
    private static int[] minimax() {
        int[] bestMove = new int[]{-1, -1};  // Ініціалізуємо найкращий хід як недійсний
        int bestScore = Integer.MIN_VALUE;  // Ініціалізуємо найкращий бал як найменший можливий

        // Перебираємо всі клітинки на дошці
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == ' ') {  // Якщо клітинка вільна
                    board[i][j] = computerPlayer; // Робимо хід комп'ютера
                    int score = minimax(board, 0, false);  // Обчислюємо бал для цього ходу
                    board[i][j] = ' ';  // Відмічаємо клітинку як вільну (скасовуємо хід)

                    // Якщо знайдено кращий хід, оновлюємо bestScore та bestMove
                    if (score > bestScore) {
                        bestScore = score;
                        bestMove[0] = i;  // Зберігаємо рядок ходу
                        bestMove[1] = j;  // Зберігаємо стовпчик ходу
                    }
                }
            }
        }
        return bestMove;  // Повертаємо найкращий хід для комп'ютера
    }

    /**
     * Рекурсивно використовує алгоритм Мінімакс для оцінки стану гри на дошці.
     * Метод оцінює поточний стан гри на дошці, враховуючи глибину і роль гравця (максимізатор або мінімізатор).
     *
     * @param board        Поточний стан дошки гри.
     * @param depth        Глибина рекурсії (поточний рівень стану гри).
     * @param isMaximizing true, якщо поточний гравець - максимізатор (комп'ютер); false, якщо мінімізатор (гравець 'X').
     * @return Оцінка стану гри в даному вузлі дерева Мінімакс.
     */
    private static int minimax(char[][] board, int depth, boolean isMaximizing) {
        if (checkWin()) {
            return isMaximizing ? -1 : 1;  // Якщо виграш гравця 'O' (максимізатор), повертаємо -1, інакше 1.
        } else if (isBoardFull()) {
            return 0; // Нічия, оцінка стану гри - 0.
        }

        if (isMaximizing) {
            int maxEval = Integer.MIN_VALUE;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board[i][j] == ' ') {
                        board[i][j] = computerPlayer;
                        int eval = minimax(board, depth + 1, false);
                        board[i][j] = ' ';
                        maxEval = Math.max(maxEval, eval);  // Обираємо найбільшу оцінку
                    }
                }
            }
            return maxEval;  // Повертаємо максимальну оцінку для максимізатора.
        } else {
            int minEval = Integer.MAX_VALUE;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board[i][j] == ' ') {
                        board[i][j] = currentPlayer;
                        int eval = minimax(board, depth + 1, true);
                        board[i][j] = ' ';
                        minEval = Math.min(minEval, eval);  // Обираємо найменшу оцінку
                    }
                }
            }
            return minEval; // Повертаємо мінімальну оцінку для мінімізатора.
        }
    }
}
