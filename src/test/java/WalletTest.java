import org.example.Card;
import org.example.Human;
import org.example.Wallet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;

public class WalletTest {
    private Wallet wallet;
    private Human owner;

    @BeforeEach
    void setUp() {
        wallet = new Wallet(5, 5, 5);
        owner = new Human("Irfan", 19);
    }

    @Test
    void testSetOwner() {
        wallet.setOwner(owner);
        assertNotNull(wallet.getOwner());
    }

    @Test
    void testHumanWithNegativeAge() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new Human("Irfan", -5));
        assertEquals("Umur tidak boleh negatif.", exception.getMessage());
    }


    @Test
    void testAccessWithoutOwner() {
        Exception exception = assertThrows(IllegalStateException.class, () -> wallet.showMoneys());
        assertEquals("Akses ditolak! Pemilik dompet belum diatur.", exception.getMessage());
    }

    @Test
    void testAddCardWithoutOwner() {
        Card card = new Card("Visa", "Credit");
        Exception exception = assertThrows(IllegalStateException.class, () -> wallet.addCard(card));
        assertEquals("Akses ditolak! Pemilik dompet belum diatur.", exception.getMessage());
    }

    @Test
    void testAddCardWithOwner() {
        wallet.setOwner(owner);
        Card card = new Card("Visa", "Credit");
        wallet.addCard(card);
        assertEquals(1, wallet.getCards().size());
    }

    @Test
    void testWithdrawCard() {
        wallet.setOwner(owner);
        Card card1 = new Card("Visa", "Credit");
        Card card2 = new Card("Mastercard", "Debit");
        wallet.addCard(card1);
        wallet.addCard(card2);

        wallet.withdrawCard("Visa");
        assertEquals(1, wallet.getCards().size());
        assertEquals("Mastercard", wallet.getCards().get(0).name);
    }

    @Test
    void testWithdrawNonExistingCard() {
        wallet.setOwner(owner);
        Card card = new Card("Visa", "Credit");
        wallet.addCard(card);

        wallet.withdrawCard("Amex");
        assertEquals(1, wallet.getCards().size());
    }

    @Test
    void testAddPaperMoneyWithoutOwner() {
        Exception exception = assertThrows(IllegalStateException.class, () -> wallet.addPaperMoney(50000, 2));
        assertEquals("Akses ditolak! Pemilik dompet belum diatur.", exception.getMessage());
    }

    @Test
    void testAddPaperMoneyWithOwner() {
        wallet.setOwner(owner);
        wallet.addPaperMoney(50000, 2);
        assertEquals(2, wallet.getPaperMoneys().size());
        assertEquals(100000, wallet.getTotalPaperMoney());
    }

    @Test
    void testAddCoinMoneyWithoutOwner() {
        Exception exception = assertThrows(IllegalStateException.class, () -> wallet.addCoinMoney(1000, 3));
        assertEquals("Akses ditolak! Pemilik dompet belum diatur.", exception.getMessage());
    }

    @Test
    void testAddCoinMoneyWithOwner() {
        wallet.setOwner(owner);
        wallet.addCoinMoney(1000, 3);
        assertEquals(3, wallet.getCoinMoneys().size());
        assertEquals(3000, wallet.getTotalCoinMoney());
    }

    @Test
    void testWithdrawMoneyWithoutOwner() {
        Exception exception = assertThrows(IllegalStateException.class, () -> wallet.withdrawMoney(10000));
        assertEquals("Akses ditolak! Pemilik dompet belum diatur.", exception.getMessage());
    }

    @Test
    void testWithdrawMoneyWithOwner() {
        wallet.setOwner(owner);
        wallet.addPaperMoney(50000, 2);
        wallet.addCoinMoney(1000, 3);
        wallet.withdrawMoney(51000);
        assertEquals(1, wallet.getPaperMoneys().size());
        assertEquals(2, wallet.getCoinMoneys().size());
        assertEquals(52000, wallet.getTotalPaperMoney() + wallet.getTotalCoinMoney());
    }

    @Test
    void testWithdrawMoreThanAvailable() {
        wallet.setOwner(owner);
        wallet.addPaperMoney(20000, 1);
        wallet.addCoinMoney(500, 2);
        wallet.withdrawMoney(30000);
        assertEquals(1, wallet.getPaperMoneys().size());
        assertEquals(2, wallet.getCoinMoneys().size());
        assertEquals(21000, wallet.getTotalPaperMoney() + wallet.getTotalCoinMoney());
    }

    @Test
    void testAddPaperMoneyExceedingLimit() {
        wallet.setOwner(owner);
        wallet.addPaperMoney(50000, 5);
        Exception exception = assertThrows(IllegalStateException.class, () -> wallet.addPaperMoney(20000, 1));
        assertEquals("Tidak bisa menambahkan lebih banyak uang kertas, slot penuh.", exception.getMessage());
    }

    @Test
    void testAddCoinMoneyExceedingLimit() {
        wallet.setOwner(owner);
        wallet.addCoinMoney(1000, 5);
        Exception exception = assertThrows(IllegalStateException.class, () -> wallet.addCoinMoney(500, 1));
        assertEquals("Tidak bisa menambahkan lebih banyak koin, slot penuh.", exception.getMessage());
    }

    @Test
    void testAddCardExceedingLimit() {
        wallet.setOwner(owner);
        wallet.addCard(new Card("Visa", "Credit"));
        wallet.addCard(new Card("MasterCard", "Debit"));
        wallet.addCard(new Card("Amex", "Credit"));
        wallet.addCard(new Card("JCB", "Debit"));
        wallet.addCard(new Card("Discover", "Credit"));

        Exception exception = assertThrows(IllegalStateException.class, () -> wallet.addCard(new Card("Diners Club", "Credit")));
        assertEquals("Tidak bisa menambahkan lebih banyak kartu, slot penuh.", exception.getMessage());
    }

}
