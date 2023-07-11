package top.spco.service;

import top.spco.domain.Cards;
import top.spco.exceptions.InvalidHandException;

import java.util.List;

/**
 * CardService 接口定义了一些基本的与牌相关的操作。
 *
 * @author SpCo
 * @version 1.1
 * @since 1.0
 */
public interface CardService {
    boolean isHandValid(List<Cards> hand);

    List<Cards> createDeck();
}
