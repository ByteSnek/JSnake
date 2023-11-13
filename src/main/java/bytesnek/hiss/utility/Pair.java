package bytesnek.hiss.utility;

/**
 * Created by SnakerBone on 13/11/2023
 **/
public class Pair<L, R>
{
    private final L left;
    private final R right;

    public Pair(L left, R right)
    {
        this.left = left;
        this.right = right;
    }

    public static <L, R> Pair<L, R> of(L left, R right)
    {
        if (left == null || right == null) {
            throw new IllegalArgumentException("Pair.of requires non null values.");
        }

        return new Pair<>(left, right);
    }

    public L getLeft()
    {
        return left;
    }

    public R getRight()
    {
        return right;
    }

    @Override
    public boolean equals(Object other)
    {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Pair<?, ?> pair)) {
            return false;
        }

        return left.equals(pair.left) && right.equals(pair.right);
    }

    @Override
    public int hashCode()
    {
        return left.hashCode() ^ right.hashCode();
    }
}
