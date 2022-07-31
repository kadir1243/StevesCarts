package vswe.stevescarts.module;

/**
 * @param modularCapacity maximum total complexity of all modules
 * @param maxEngines
 * @param maxAddons
 * @param maxComplexity maximum complexity of each individual module
 */
public record HullData(int modularCapacity, int maxEngines, int maxAddons, int maxComplexity) {
}
