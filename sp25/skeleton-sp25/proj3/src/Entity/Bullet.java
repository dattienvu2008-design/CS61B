//package Entity;
//
//import renderengine.TETileRenderer;
//import worldassets.Direction;
//import worldassets.tile.Tile;
//
//public class Bullet extends Entity{
//    private Direction direction;
//    public final double damage;
//
//    public Bullet(double damage, TETileRenderer skin, Tile currentTile) {
//        super(999999999, -99999999, skin, currentTile);
//        this.damage = damage;
//    }
//
//    @Override
//    public void move(Direction direction) {
//        Tile destination = direction.getTile(getCurrentTile());
//        if (destination != null && !destination.getTileType().blockable) {
//            if (((destination.hasEntity() && !destination.getEntity().getSkin().blockable) || !destination.hasEntity())) {
//                destination.setEntity(this);
//                getCurrentTile().removeEntity();
//                setCurrentTile(destination);
//            }
//        }
//    }
//}

//Need further modified
