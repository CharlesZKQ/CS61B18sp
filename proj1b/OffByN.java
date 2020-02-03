public class OffByN implements CharacterComparator {
    private int offByN;
    public OffByN(int N){
        offByN = N;
    }
    @Override
    public boolean equalChars(char x, char y) {
        return (x - offByN == y || x + offByN == y);
    }
}
