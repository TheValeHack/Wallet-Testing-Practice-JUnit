package org.example;

import java.util.ArrayList;

public class Wallet {
    private int totalPaperMoney = 0;
    private int totalCoinMoney = 0;
    private int cardSlot;
    private int paperMoneySlot;
    private int coinMoneySlot;
    private Human owner;
    private ArrayList<Card> cards;
    private ArrayList<Integer> paperMoneys;
    private ArrayList<Integer> coinMoneys;

    public Wallet(int cardSlot, int paperMoneySlot, int coinMoneySlot) {
        if (cardSlot < 0 || paperMoneySlot < 0 || coinMoneySlot < 0) {
            throw new IllegalArgumentException("Jumlah slot tidak boleh negatif");
        }

        this.cardSlot = cardSlot;
        this.paperMoneySlot = paperMoneySlot;
        this.coinMoneySlot = coinMoneySlot;
        this.cards = new ArrayList<>();
        this.paperMoneys = new ArrayList<>();
        this.coinMoneys = new ArrayList<>();
    }

    private void checkOwner() {
        if (owner == null) {
            throw new IllegalStateException("Akses ditolak! Pemilik dompet belum diatur.");
        }
    }

    public void setOwner(Human newOwner) {
        if (newOwner == null) {
            throw new IllegalArgumentException("Owner tidak boleh null");
        }
        this.owner = newOwner;
    }
    public Human getOwner(){
        return owner;
    }

    public ArrayList<Card> getCards() {
        checkOwner();
        return cards;
    }

    public void showCards() {
        checkOwner();
        if (cards.isEmpty()) {
            System.out.println("Dompet tidak memiliki kartu.");
            return;
        }
        for (int i = 0; i < cards.size(); i++) {
            System.out.println((i + 1) + ". Kartu " + cards.get(i).name + " bertipe " + cards.get(i).cardType);
        }
    }

    public void addCard(Card newCard) {
        checkOwner();
        if (newCard == null) {
            System.out.println("Kartu tidak boleh null.");
            return;
        }
        if (cards.size() < cardSlot) {
            cards.add(newCard);
            System.out.println("Kartu berhasil ditambahkan.");
        } else {
            throw new IllegalStateException("Tidak bisa menambahkan lebih banyak kartu, slot penuh.");
        }
    }

    public void addPaperMoney(int money, int quantity) {
        checkOwner();
        if (money <= 0 || quantity <= 0) {
            System.out.println("Nilai uang dan jumlah harus lebih dari 0.");
            return;
        }
        if ((paperMoneys.size() + quantity) <= paperMoneySlot) {
            for (int i = 0; i < quantity; i++) {
                paperMoneys.add(money);
            }
            totalPaperMoney += money * quantity;
            System.out.println("Uang kertas berhasil ditambahkan.");
        } else {
            throw new IllegalStateException("Tidak bisa menambahkan lebih banyak uang kertas, slot penuh.");
        }
    }

    public void addCoinMoney(int money, int quantity) {
        checkOwner();
        if (money <= 0 || quantity <= 0) {
            System.out.println("Nilai uang koin dan jumlah harus lebih dari 0.");
            return;
        }
        if ((coinMoneys.size() + quantity) <= coinMoneySlot) {
            for (int i = 0; i < quantity; i++) {
                coinMoneys.add(money);
            }
            totalCoinMoney += money * quantity;
            System.out.println("Uang koin berhasil ditambahkan.");
        } else {
            throw new IllegalStateException("Tidak bisa menambahkan lebih banyak koin, slot penuh.");
        }
    }

    public void withdrawCard(String cardName) {
        checkOwner();
        if (cardName == null || cardName.trim().isEmpty()) {
            System.out.println("Nama kartu tidak boleh kosong.");
            return;
        }

        for (int i = 0; i < cards.size(); i++) {
            if (cards.get(i).name.equalsIgnoreCase(cardName)) {
                cards.remove(i);
                System.out.println("Kartu " + cardName + " berhasil diambil dari dompet.");
                return;
            }
        }

        System.out.println("Kartu " + cardName + " tidak ditemukan dalam dompet.");
    }


    public void withdrawMoney(int amount) {
        checkOwner();
        if (amount <= 0) {
            System.out.println("Jumlah yang ingin ditarik harus lebih dari 0.");
            return;
        }
        if ((totalCoinMoney + totalPaperMoney) < amount) {
            System.out.println("Saldo tidak mencukupi.");
            return;
        }

        int remaining = amount;

        for (int i = paperMoneys.size() - 1; i >= 0 && remaining > 0; i--) {
            int bill = paperMoneys.get(i);
            if (bill <= remaining) {
                remaining -= bill;
                totalPaperMoney -= bill;
                paperMoneys.remove(i);
            }
        }

        for (int i = coinMoneys.size() - 1; i >= 0 && remaining > 0; i--) {
            int coin = coinMoneys.get(i);
            if (coin <= remaining) {
                remaining -= coin;
                totalCoinMoney -= coin;
                coinMoneys.remove(i);
            }
        }

        if (remaining == 0) {
            System.out.println("Berhasil menarik uang sebesar " + amount);
        } else {
            System.out.println("Tidak bisa menarik uang dengan nominal yang sesuai.");
        }
    }

    public ArrayList<Integer> getCoinMoneys() {
        checkOwner();
        return coinMoneys;
    }

    public ArrayList<Integer> getPaperMoneys() {
        checkOwner();
        return paperMoneys;
    }

    public int getTotalPaperMoney(){
        checkOwner();
        return totalPaperMoney;
    }
    public int getTotalCoinMoney(){
        checkOwner();
        return totalCoinMoney;
    }

    public void showMoneys() {
        checkOwner();
        System.out.println("Total nilai uang kertas: " + totalPaperMoney + " (" + paperMoneys.size() + " lembar)");
        System.out.println("Total nilai uang koin: " + totalCoinMoney + " (" + coinMoneys.size() + " keping)");
        System.out.println("Total nilai uang dalam dompet: " + (totalCoinMoney + totalPaperMoney));
    }
}
