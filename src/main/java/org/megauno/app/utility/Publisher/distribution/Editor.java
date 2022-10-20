package org.megauno.app.utility.Publisher.distribution;

public interface Editor<newsPaper, story> {
    newsPaper extractStory(story np);
}
