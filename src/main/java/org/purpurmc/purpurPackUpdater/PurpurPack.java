package org.purpurmc.purpurPackUpdater;

/**
 * Represents a Purpur datapack with a project ID, version number, and name.
 *
 * @param projectID could possibly be any pack with an ID and version number but yaknow
 */
public record PurpurPack(String projectID, String currentVersion, String name) {
    /**
     * Constructs a new PurpurPack object with the specified project ID, version string, and name.
     *
     * @param projectID      The project ID of the datapack.
     * @param currentVersion The version string of the datapack.
     * @param name           The name of the datapack.
     */
    public PurpurPack {
    }

    /**
     * Gets the project ID of this PurpurPack.
     *
     * @return The project ID.
     */
    @Override
    public String projectID() {
        return projectID;
    }

    /**
     * Gets the version string of this PurpurPack.
     *
     * @return The version string.
     */
    @Override
    public String currentVersion() {
        return currentVersion;
    }

    /**
     * Gets the name of this PurpurPack.
     *
     * @return The name of the datapack.
     */
    @Override
    public String name() {
        return name;
    }

    /**
     * Returns a string representation of this PurpurPack, containing the project ID, version string, and name.
     *
     * @return A string representation of this PurpurPack.
     */
    public String toString() {
        return projectID + "\t" + currentVersion + "\t" + name;
    }
}
