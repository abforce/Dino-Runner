package ir.abforce.dinorunner.objects;

import org.andengine.entity.text.TickerText;
import org.andengine.util.adt.align.HorizontalAlign;

import ir.abforce.dinorunner.custom.SSprite;
import ir.abforce.dinorunner.managers.RM;

/**
 * Created by Ali Reza on 8/31/15.
 */
public class DinoQuote extends SSprite {
    private TickerText mText;
    private float mElapsed = 0;
    private String[] mQuotes = new String[]{
            "Hi\nthere!",
            "I'm\nDino :)",
            "love\nto\nrun",
            "here\nwe\ngo"
    };
    private int quoteIndex = mQuotes.length;

    public DinoQuote(float pX, float pY) {
        super(pX, pY, RM.txQuoteFrame);
        float x = RM.txQuoteFrame.getWidth() / 2;
        float y = RM.txQuoteFrame.getHeight() / 2;
        mText = new TickerText(x, y, RM.fMain, "                    ", new TickerText.TickerTextOptions(HorizontalAlign.CENTER, 8), RM.VBO);
        mText.setColor(0.33f, 0.33f, 0.33f);
        this.attachChild(mText);
    }

    public void start(){
        quoteIndex = -1;
    }

    @Override
    protected void onManagedUpdate(float pSecondsElapsed) {
        super.onManagedUpdate(pSecondsElapsed);
        mElapsed += pSecondsElapsed;
        if(mElapsed > 2 && quoteIndex < mQuotes.length - 1){
            mElapsed = 0;
            quoteIndex += 1;
            mText.reset();
            mText.setText(mQuotes[quoteIndex]);
        }
    }
}
