## Things to do when adding a new module
1. Create a subclass of `MinecartModule` and implement necessary methods
2. Implement the `Configurable` interface if the module should appear on the cart screen
3. Create a `MinecartModuleType` instance in the `StevesCartsModuleTypes` class
4. Add the item model and item texture
5. Add the module's translation key to the language file(s)
6. Add the module to any module tags if applicable
7. Add a recipe for the module if applicable
8. Create model(s) for the module
9. Register the module's renderer in `ModuleRenderDispatcher`, either using `GenericRenderer` or a subclass of `ModuleRenderer`
