package alc;

import Pair.Pair;
import alc.Components.ElementComponent;
import alc.Screens.GameScreen;
import engine.Components.*;
import engine.GameObject;
import engine.GameWorld;
import engine.Resource;
import engine.Shape.AAB;
import engine.Sprite;
import engine.support.Vec2d;
import javafx.scene.image.Image;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class AlcGame {
    private static Integer zIndex = 2;

    private final Resource<Sprite> elementImages;

    private Integer numberOfElements;
    private final GameScreen screen;

    private final GameWorld gameWorld;

    private final Map<Pair<Element, Element>, List<Element>> combinations;
    private final Set<Element> foundElements;
    private final Set<Element> finalElements;
    public AlcGame(GameScreen screen, GameWorld gameWorld) {
        this.numberOfElements = 0;
        this.screen = screen;
        this.gameWorld = gameWorld;
        this.combinations = new HashMap<>();
        this.foundElements = new HashSet<>();
        this.finalElements = new HashSet<>();

        elementImages = new Resource<>();
        elementImages.putResource("FIRE", new Sprite(new Image("file:.\\src\\main\\java\\alc\\ElementImages\\FIRE.png"), new Vec2d(1, 1)));
        foundElements.add(Element.FIRE);
        elementImages.putResource("WATER", new Sprite(new Image("file:.\\src\\main\\java\\alc\\ElementImages\\WATER.png"), new Vec2d(1, 1)));
        foundElements.add(Element.WATER);
        elementImages.putResource("EARTH", new Sprite(new Image("file:.\\src\\main\\java\\alc\\ElementImages\\EARTH.png"), new Vec2d(1, 1)));
        foundElements.add(Element.EARTH);
        elementImages.putResource("AIR", new Sprite(new Image("file:.\\src\\main\\java\\alc\\ElementImages\\AIR.png"), new Vec2d(1, 1)));
        foundElements.add(Element.AIR);

        elementImages.putResource("STEAM", new Sprite(new Image("file:.\\src\\main\\java\\alc\\ElementImages\\STEAM.png"), new Vec2d(1, 1)));
        combinations.put(new Pair<>(Element.AIR, Element.FIRE), List.of(Element.STEAM));
        combinations.put(new Pair<>(Element.FIRE, Element.AIR), List.of(Element.STEAM));
        elementImages.putResource("LAVA", new Sprite(new Image("file:.\\src\\main\\java\\alc\\ElementImages\\LAVA.png"), new Vec2d(1, 1)));
        combinations.put(new Pair<>(Element.EARTH, Element.FIRE), List.of(Element.LAVA));
        combinations.put(new Pair<>(Element.FIRE, Element.EARTH), List.of(Element.LAVA));
        elementImages.putResource("PRESSURE", new Sprite(new Image("file:.\\src\\main\\java\\alc\\ElementImages\\PRESSURE.png"), new Vec2d(1, 1)));
        combinations.put(new Pair<>(Element.AIR, Element.AIR), List.of(Element.PRESSURE));
        elementImages.putResource("ENERGY", new Sprite(new Image("file:.\\src\\main\\java\\alc\\ElementImages\\ENERGY.png"), new Vec2d(1, 1)));
        combinations.put(new Pair<>(Element.FIRE, Element.FIRE), List.of(Element.ENERGY));
        elementImages.putResource("MUD", new Sprite(new Image("file:.\\src\\main\\java\\alc\\ElementImages\\MUD.png"), new Vec2d(1, 1)));
        combinations.put(new Pair<>(Element.EARTH, Element.WATER), List.of(Element.MUD));
        combinations.put(new Pair<>(Element.WATER, Element.EARTH), List.of(Element.MUD));
        elementImages.putResource("DUST", new Sprite(new Image("file:.\\src\\main\\java\\alc\\ElementImages\\DUST.png"), new Vec2d(1, 1)));
        combinations.put(new Pair<>(Element.EARTH, Element.AIR), List.of(Element.DUST));
        combinations.put(new Pair<>(Element.AIR, Element.EARTH), List.of(Element.DUST));
        elementImages.putResource("EARTHQUAKE", new Sprite(new Image("file:.\\src\\main\\java\\alc\\ElementImages\\EARTHQUAKE.png"), new Vec2d(1, 1)));
        combinations.put(new Pair<>(Element.EARTH, Element.ENERGY), List.of(Element.EARTHQUAKE));
        combinations.put(new Pair<>(Element.ENERGY, Element.EARTH), List.of(Element.EARTHQUAKE));
        elementImages.putResource("SMOKE", new Sprite(new Image("file:.\\src\\main\\java\\alc\\ElementImages\\SMOKE.png"), new Vec2d(1, 1)));
        combinations.put(new Pair<>(Element.AIR, Element.FIRE), List.of(Element.SMOKE));
        combinations.put(new Pair<>(Element.FIRE, Element.AIR), List.of(Element.SMOKE));
        elementImages.putResource("OBSIDIAN", new Sprite(new Image("file:.\\src\\main\\java\\alc\\ElementImages\\OBSIDIAN.png"), new Vec2d(1, 1)));
        combinations.put(new Pair<>(Element.WATER, Element.LAVA), List.of(Element.OBSIDIAN));
        combinations.put(new Pair<>(Element.LAVA, Element.WATER), List.of(Element.OBSIDIAN));
        finalElements.add(Element.OBSIDIAN);
        elementImages.putResource("PUDDLE", new Sprite(new Image("file:.\\src\\main\\java\\alc\\ElementImages\\PUDDLE.png"), new Vec2d(1, 1)));
        combinations.put(new Pair<>(Element.WATER, Element.WATER), List.of(Element.PUDDLE));
        elementImages.putResource("LAND", new Sprite(new Image("file:.\\src\\main\\java\\alc\\ElementImages\\LAND.png"), new Vec2d(1, 1)));
        combinations.put(new Pair<>(Element.EARTH, Element.EARTH), List.of(Element.LAND));
        elementImages.putResource("MIST", new Sprite(new Image("file:.\\src\\main\\java\\alc\\ElementImages\\MIST.png"), new Vec2d(1, 1)));
        combinations.put(new Pair<>(Element.WATER, Element.AIR), List.of(Element.MIST));
        combinations.put(new Pair<>(Element.AIR, Element.WATER), List.of(Element.MIST));
        elementImages.putResource("ERUPTION", new Sprite(new Image("file:.\\src\\main\\java\\alc\\ElementImages\\ERUPTION.png"), new Vec2d(1, 1)));
        elementImages.putResource("GRANITE", new Sprite(new Image("file:.\\src\\main\\java\\alc\\ElementImages\\GRANITE.png"), new Vec2d(1, 1)));
        combinations.put(new Pair<>(Element.LAVA, Element.PRESSURE), List.of(Element.ERUPTION, Element.GRANITE));
        combinations.put(new Pair<>(Element.PRESSURE, Element.LAVA), List.of(Element.ERUPTION, Element.GRANITE));
        finalElements.add(Element.ERUPTION);
        finalElements.add(Element.GRANITE);
        elementImages.putResource("GUNPOWDER", new Sprite(new Image("file:.\\src\\main\\java\\alc\\ElementImages\\GUNPOWDER.png"), new Vec2d(1, 1)));
        combinations.put(new Pair<>(Element.ENERGY, Element.DUST), List.of(Element.GUNPOWDER));
        combinations.put(new Pair<>(Element.DUST, Element.ENERGY), List.of(Element.GUNPOWDER));
        elementImages.putResource("EXPLOSION", new Sprite(new Image("file:.\\src\\main\\java\\alc\\ElementImages\\EXPLOSION.png"), new Vec2d(1, 1)));
        combinations.put(new Pair<>(Element.FIRE, Element.GUNPOWDER), List.of(Element.EXPLOSION));
        combinations.put(new Pair<>(Element.GUNPOWDER, Element.FIRE), List.of(Element.EXPLOSION));

        // Background to Game World
        Sprite backgroundSprite = new Sprite(new Image("file:.\\src\\main\\java\\alc\\ElementImages\\" + "BACKGROUND" + ".png"), new Vec2d(1,1));
        TransformComponent backgroundTransformComponent = new TransformComponent(Constants.backgroundPosition, new Vec2d(543,543));
        GameObject background = new GameObject(backgroundTransformComponent, 0);
        background.addComponent(new SpriteComponent(background, backgroundSprite, new Vec2d(0,0)));
        this.gameWorld.addGameObject(background);

        // Trash Area
        Sprite trashSprite = new Sprite(new Image("file:.\\src\\main\\java\\alc\\ElementImages\\" + "TRASH1" + ".png"), new Vec2d(1,1));
        TransformComponent trashTransformComponent = new TransformComponent(Constants.trashPosition, new Vec2d(125,125));
        GameObject trash = new GameObject(trashTransformComponent, 1);
        trash.addComponent(new SpriteComponent(trash, trashSprite, new Vec2d(0,0)));
        trash.addComponent(new ElementComponent(Element.NULL));
        trash.addComponent(new CollisionComponent(trash, new AAB(trashTransformComponent.getCurrentGameSpacePosition(), trashTransformComponent.getSize()), 0, true, false){
            @Override
            public void onCollide(Collision collision) {
                onCollision(trash, collision.getCollidedObject());
            }
        });
        this.gameWorld.addGameObject(trash);
    }

    public Integer getNumberOfElements() {
        return this.numberOfElements;
    }

    public void incrementNumberOfElements() {
        this.numberOfElements += 1;
    }

    public Set<Element> getFinalElements() {
        return this.finalElements;
    }

    public void makeElement(Element e, Vec2d startCoordinate, boolean gameCoordinate) {
        Sprite sprite = elementImages.getResource(e.name());
        Vec2d startGameCoordinate = startCoordinate;
        if (!gameCoordinate) {
            startGameCoordinate = screen.getViewport().convertScreenCoordinateToGame(startCoordinate);
        }

        TransformComponent transformComponent = new TransformComponent(startGameCoordinate, Constants.gameObjectSize);
        GameObject o = new GameObject(transformComponent, zIndex);
        zIndex += 1;
        o.addComponent(new ElementComponent(e));
        o.addComponent(new SpriteComponent(o, sprite, new Vec2d(0,0)));
        o.addComponent(new MouseDragComponent(o, startGameCoordinate, startGameCoordinate));
        o.addComponent(new CollisionComponent(o, new AAB(transformComponent.getCurrentGameSpacePosition(), transformComponent.getSize()), 0, true, false){
            @Override
            public void onCollide(Collision collision) {
                onCollision(this.gameObject, collision.getCollidedObject());
            }
        });

        gameWorld.addGameObject(o);
    }

    private void onCollision(GameObject g1, GameObject g2) {
        Element element1 = ((ElementComponent) g1.getComponent("element")).getElement();
        Element element2 = ((ElementComponent) g2.getComponent("element")).getElement();

        if ((element1 == Element.NULL) || (element2 == Element.NULL)) {
            if (element1 == Element.NULL) {
                gameWorld.removeGameObject(g2);
            } else {
                gameWorld.removeGameObject(g1);
            }
        } else {
            Pair<Element, Element> merge = new Pair<>(element1, element2);
            if (combinations.containsKey(merge)) {
                List<Element> newElements = combinations.get(merge);
                for (Element ne: newElements) {
                    if (!foundElements.contains(ne)) {
                        foundElements.add(ne);
                        gameWorld.removeGameObject(g1);
                        gameWorld.removeGameObject(g2);
                        screen.elementButtonGenerator(ne);
                        makeElement(ne, g1.getTransform().getCurrentGameSpacePosition(), true);
                    } else {
                        gameWorld.removeGameObject(g1);
                        gameWorld.removeGameObject(g2);
                        makeElement(ne, g1.getTransform().getCurrentGameSpacePosition(), true);
                    }
                }
            }
        }
    }
}
