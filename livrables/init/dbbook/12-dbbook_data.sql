INSERT INTO dbbook.book
(id, title, author, genre, language, editor, summary, release_date )
VALUES
(1, 'La jeune fille et la nuit','Guillaume Musso', 'thriller', 'français', 'Calmann-Lévy', 'Un campus prestigieux figé sous la neige. Trois amis liés par un secret tragique. Une jeune fille emportée par la nuit.', '24/04/2018' ),
(2, 'Sérotonine','Michel Houellebecq', 'fiction', 'français', 'Flammarion', 'Le narrateur de Sérotonine approuverait sans réserve. Son récit traverse une France qui piétine ses traditions, banalise ses villes, détruit ses campagnes au bord de la révolte. ', '04/01/2019'),
(3, 'Tous les hommes n’habitent pas le monde de la même façon', 'Jean-Paul Dubois', 'roman', 'français', 'éditions de lOlivier', 'Le prix Goncourt 2019 retrace la vie de Paul Hansen, super intendant à L’Excelsior, une résidence où il déploie ses talents de concierge, de gardien et de réparateur des âmes.', '14/08/2019'),
(4, 'Changer leau des fleurs','Valérie Perrin', 'roman', 'français', 'Albin Michel' , 'Violette Toussaint est garde-cimetière dans une petite ville de Bourgogne. Les gens de passage et les habitués viennent se réchauffer dans sa loge où rires et larmes se mélangent au café quelle leur offre. Son quotidien est rythmé par leurs confidences.', '28/02/2018'),
(5, 'Livre5','auteur', 'essai', 'français', 'editeur', 'resume du livre', '01/01/2020' ),
(6, 'Livre6','auteur', 'polar', 'français', 'editeur', 'resume du livre', '01/01/2020' ),
(7, 'Livre7','auteur', 'science-fiction', 'français', 'editeur', 'resume du livre', '01/01/2020' ),
(8, 'Livre8','auteur', 'fantastique', 'français', 'editeur', 'resume du livre', '01/01/2020' ),
(9, 'Livre9','auteur', 'conte', 'français', 'editeur', 'resume du livre', '01/01/2020' ),
(10, 'Livre10','auteur', 'roman', 'français', 'editeur', 'resume du livre', '01/01/2020' );

INSERT INTO dbbook.copy
(id, book)
VALUES
(1,1),
(2,1),
(3,2),
(4,2),
(5,3),
(6,4),
(7,5),
(8,6),
(9,7),
(10,8);


