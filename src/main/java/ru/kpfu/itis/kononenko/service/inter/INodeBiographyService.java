package ru.kpfu.itis.kononenko.service.inter;

public interface INodeBiographyService {
    void saveBiography(Long nodeId, String biography);
    void deleteBiography(Long nodeId);
    String getBiography(Long nodeId);
}
