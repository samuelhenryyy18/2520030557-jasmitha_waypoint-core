
package waypoint.model;

import java.util.Currency;
import java.util.List;
import java.util.ArrayList;
import java.util.Objects;

public final class Money implements Comparable<Money> {

    public static final Currency INR = Currency.getInstance("INR");
    public static final Currency USD = Currency.getInstance("USD");

    private final long paise;
    private final Currency currency;

    private Money(long paise, Currency currency) {
        this.paise = paise;
        this.currency = Objects.requireNonNull(currency);
    }

    public static Money ofMinor(long paise, Currency currency) {
        return new Money(paise, currency);
    }

    public static Money rupees(long rupees) {
        return new Money(Math.multiplyExact(rupees, 100), INR);
    }

    public Money plus(Money other) {
        checkCurrency(other);
        return new Money(Math.addExact(paise, other.paise), currency);
    }

    public Money minus(Money other) {
        checkCurrency(other);
        return new Money(Math.subtractExact(paise, other.paise), currency);
    }

    public Money times(int multiplier) {
        return new Money(Math.multiplyExact(paise, multiplier), currency);
    }

    public boolean isNegative() {
        return paise < 0;
    }

    @Override
    public int compareTo(Money other) {
        checkCurrency(other);
        return Long.compare(paise, other.paise);
    }

    public List<Money> allocate(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("Number of shares must be positive");
        }

        List<Money> shares = new ArrayList<>();

        long base = paise / n;
        long remainder = paise % n;

        for (int i = 0; i < n; i++) {
            long share = base;

            if (remainder > 0 && i < remainder) {
                share++;
            } else if (remainder < 0 && i < -remainder) {
                share--;
            }

            shares.add(new Money(share, currency));
        }

        return List.copyOf(shares);
    }

    private void checkCurrency(Money other) {
        Objects.requireNonNull(other);

        if (!currency.equals(other.currency)) {
            throw new CurrencyMismatch();
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Money other)) return false;

        return paise == other.paise &&
               currency.equals(other.currency);
    }

    @Override
    public int hashCode() {
        return Objects.hash(paise, currency);
    }

    @Override
    public String toString() {
        return currency.getCurrencyCode() + " " +
               (paise / 100) + "." +
               String.format("%02d", Math.abs(paise % 100));
    }
}
