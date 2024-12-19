package ru.kpfu.itis.kononenko.service.inter;

import ru.kpfu.itis.kononenko.entity.Node;

public interface IFamilyService {
    void addInitialNode(Node selfNode);
    Node addNewNode(Node newMember, Long childId);
}
