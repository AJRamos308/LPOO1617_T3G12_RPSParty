package model.entities;

/**
 * Created by bibib on 28/04/2017.
 */

public class ScissorMiniGameModel extends EntityModel {

    /**
     * construtor do modelo para a tesoura no mini jogo
     *
     * @param x coordenada x
     * @param y coordenada y
     * @param rotation rotacao
     */
    public ScissorMiniGameModel(float x, float y, float rotation) {
        super(x, y, rotation);
    }

    @Override
    public ModelType getType() {
        return ModelType.SCISSORMINIGAME;
    }
}
