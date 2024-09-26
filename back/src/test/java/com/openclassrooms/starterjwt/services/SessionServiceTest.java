package com.openclassrooms.starterjwt.services;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.openclassrooms.starterjwt.exception.BadRequestException;
import com.openclassrooms.starterjwt.exception.NotFoundException;
import com.openclassrooms.starterjwt.models.Session;
import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.repository.SessionRepository;
import com.openclassrooms.starterjwt.repository.UserRepository;

public class SessionServiceTest {
    @Mock
    private SessionRepository sessionRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private SessionService sessionService;
     
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this); // Initialise les mocks avant chaque test
    }

    // Test pour vérifier qu'une NotFoundException est levée lorsque la session n'est pas trouvée
    @Test
    public void testParticipateWhenSessionNotFound() {
        // Simule le cas où la session n'est pas trouvée
        when(sessionRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Simule le cas où l'utilisateur est trouvé
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(new User()));

        // Vérifie que la NotFoundException est lancée
        assertThrows(NotFoundException.class, () -> sessionService.participate(1L, 1L));
    }

    // Test pour vérifier qu'une NotFoundException est levée lorsque l'utilisateur n'est pas trouvé
    @Test
    public void testParticipateWhenUserNotFound() {
        // Simule le cas où la session est trouvée
        when(sessionRepository.findById(anyLong())).thenReturn(Optional.of(new Session()));

        // Simule le cas où l'utilisateur n'est pas trouvé
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Vérifie que la NotFoundException est lancée
        assertThrows(NotFoundException.class, () -> sessionService.participate(1L, 1L));
    }

    // Test pour vérifier qu'une BadRequestException est levée lorsque l'utilisateur participe déjà à la session
    @Test
    public void testParticipateWhenUserAlreadyParticipating() {
        // Crée un utilisateur et une session où l'utilisateur participe déjà
        User user = new User();
        user.setId(2L);
        
        Session session = new Session();
        session.setId(2L);
        session.setUsers(new ArrayList<>());  // Initialiser la liste avant de l'utiliser
        session.getUsers().add(user);

        // Simule le cas où la session et l'utilisateur sont trouvés
        when(sessionRepository.findById(anyLong())).thenReturn(Optional.of(session));
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));

        // Vérifie que la BadRequestException est lancée
        assertThrows(BadRequestException.class, () -> sessionService.participate(2L, 2L));
    }

    @Test
    public void testParticipateSuccess() {
        // Créer un utilisateur et une session initialement vide
        User user = new User();
        user.setId(1L);
        
        Session session = new Session();
        session.setId(1L);
        session.setUsers(new ArrayList<>());  // Initialiser la liste d'utilisateurs

        // Simuler le comportement des repositories pour renvoyer la session et l'utilisateur existants
        when(sessionRepository.findById(1L)).thenReturn(Optional.of(session));
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        // Appeler la méthode à tester
        sessionService.participate(1L, 1L);

        // Vérifier que l'utilisateur a bien été ajouté à la session
        assertTrue(session.getUsers().contains(user));

        // Vérifier que la session a bien été sauvegardée avec l'utilisateur ajouté
        verify(sessionRepository).save(session);  // Vérifie que save() a été appelé sur le repository
    }

    @Test
    public void testNoLongerParticipateSuccess() {
        // Créer un utilisateur et une session où l'utilisateur participe
        User user = new User();
        user.setId(1L);
        
        Session session = new Session();
        session.setId(1L);
        session.setUsers(new ArrayList<>());  // Initialiser la session avec l'utilisateur
        session.getUsers().add(user);


        // Simuler le comportement du sessionRepository pour renvoyer la session existante
        when(sessionRepository.findById(1L)).thenReturn(Optional.of(session));

        // Appeler la méthode à tester
        sessionService.noLongerParticipate(1L, 1L);

        // Vérifier que l'utilisateur a bien été retiré de la session
        assertFalse(session.getUsers().contains(user));

        // Vérifier que la session a bien été sauvegardée sans l'utilisateur
        verify(sessionRepository).save(session);  // Vérifie que save() a bien été appelé
    }

}
