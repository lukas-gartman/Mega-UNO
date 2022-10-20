package org.megauno.app.utility.Publisher.distrobution;

public interface Editor<newsPaper, story> {
    newsPaper extractStory(story np);
}
