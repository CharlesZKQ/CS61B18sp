public class Palindrome {
    /* return a Deque of all the characters for the input word. */
    public Deque<Character> wordToDeque(String word) {
        Deque<Character> CharInWord = new ArrayDeque<>();
        for (int i = 0; i < word.length(); i++){
            CharInWord.addLast(word.charAt(i));
        }
        return CharInWord;
    }

    public boolean isPalindrome(String word) {
        return isPalindrome(wordToDeque(word));
    }
    private boolean isPalindrome(Deque<Character> CharInWord){
        while(CharInWord.size() > 1){
            return CharInWord.removeFirst() == CharInWord.removeLast() && isPalindrome(CharInWord);
        }
        return true;
    }

    public boolean isPalindrome(String word, CharacterComparator cc){
        return isPalindrome(wordToDeque(word), cc);
    }

    private boolean isPalindrome(Deque<Character> CharInWord, CharacterComparator cc){
        while(CharInWord.size() > 1){
            return cc.equalChars(CharInWord.removeFirst(), CharInWord.removeLast()) && isPalindrome(CharInWord, cc);
        }
        return true;
    }
}
