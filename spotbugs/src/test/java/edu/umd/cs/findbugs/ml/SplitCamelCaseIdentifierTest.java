package edu.umd.cs.findbugs.ml;

import java.util.Collection;

import org.junit.Assert;
import org.junit.Test;

import edu.umd.cs.findbugs.detect.BuildObligationPolicyDatabase;
import edu.umd.cs.findbugs.util.SplitCamelCaseIdentifier;

public class SplitCamelCaseIdentifierTest {

    @Test
    public void testSplitLowerCamelCase() {
        SplitCamelCaseIdentifier sut = new SplitCamelCaseIdentifier("displayGUIWindow");
        Collection<String> words = sut.split();
        checkContents(words, "display", "gui", "window");
    }

    @Test
    public void testSplit2() {
        SplitCamelCaseIdentifier sut = new SplitCamelCaseIdentifier("DisplayGUIWindow");
        Collection<String> words = sut.split();
        checkContents(words, "display", "gui", "window");
    }

    @Test
    public void testSplitLong() {
        SplitCamelCaseIdentifier sut = new SplitCamelCaseIdentifier("nowIsTheWINTEROfOURDiscontent");
        Collection<String> words = sut.split();
        checkContents(words, "now", "is", "the", "winter", "of", "our", "discontent");
    }

    @Test
    public void testAllLower() {
        SplitCamelCaseIdentifier sut = new SplitCamelCaseIdentifier("foobar");
        Collection<String> words = sut.split();
        checkContents(words, "foobar");
    }

    @Test
    public void testAllUpper() {
        SplitCamelCaseIdentifier sut = new SplitCamelCaseIdentifier("NSA");
        Collection<String> words = sut.split();
        checkContents(words, "nsa");
    }

    @Test
    public void testCapitalized() {
        SplitCamelCaseIdentifier sut = new SplitCamelCaseIdentifier("Maryland");
        Collection<String> words = sut.split();
        checkContents(words, "maryland");
    }

    private void checkContents(Collection<String> words, String... expected) {
        Assert.assertEquals(expected.length, words.size());
        for (String anExpected : expected) {
            Assert.assertTrue(words.contains(anExpected));
        }
    }

    /**
     * {@link BuildObligationPolicyDatabase} uses {@link SplitCamelCaseIdentifier} to separate method name, and it
     * should separate {@literal "$closeResource"} into {@literal "$"}, {@literal "close"} and {@literal "resource"} to
     * handle generated $closeResource method as resource closer.
     * 
     * @see BuildObligationPolicyDatabase#INFER_CLOSE_METHODS
     */
    @Test
    public void testDollarMark() {
        SplitCamelCaseIdentifier sut = new SplitCamelCaseIdentifier("$closeResource");
        Collection<String> words = sut.split();
        checkContents(words, "$", "close", "resource");
    }
}
