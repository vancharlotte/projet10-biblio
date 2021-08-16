INSERT INTO dbbook.book
(id, title, author, genre, language, editor, summary, release_date )
VALUES
(1, N'La jeune fille et la nuit','Guillaume Musso', 'thriller',  N'français', N'Calmann-Lévy', N'Un campus prestigieux figé sous la neige. Trois amis liés par un secret tragique. Une jeune fille emportée par la nuit.', '24/04/2018' ),
(2, N'Sérotonine',N'Michel Houellebecq', N'fiction', N'français', N'Flammarion', N'Le narrateur de Sérotonine approuverait sans réserve. Son récit traverse une France qui piétine ses traditions, détruit ses campagnes au bord de la révolte. ', '04/01/2019'),
(3, N'Tous les hommes n’habitent pas le monde de la même façon', N'Jean-Paul Dubois', N'roman', N'français', N'éditions de lOlivier', N'Le prix Goncourt 2019 retrace la vie de Paul Hansen qui déploie ses talents de concierge, de gardien et de réparateur des âmes.', '14/08/2019'),
(4, N'Changer leau des fleurs',N'Valérie Perrin', N'roman', N'français', N'Albin Michel' , N'Violette Toussaint est garde-cimetière dans une petite ville de Bourgogne. Les gens de passage et les habitués viennent se réchauffer dans sa loge.', '28/02/2018'),
(5, N'Livre5', N'auteur', N'essai', N'français', N'éditeur', N'résumé du livre', '01/01/2020' ),
(6, N'Livre6', N'auteur', N'polar', N'français', N'éditeur', N'résumé du livre', '01/01/2020' ),
(7, N'Livre7', N'auteur', N'science-fiction', N'français', N'éditeur', N'résumé du livre', '01/01/2020' ),
(8, N'Livre8', N'auteur', N'fantastique', N'français', N'éditeur', N'résumé du livre', '01/01/2020' ),
(9, N'Livre9', N'auteur', N'conte', N'français', N'éditeur', N'résumé du livre', '01/01/2020' ),
(10, N'Livre10', N'auteur', N'roman', N'français', N'éditeur', N'résumé du livre', '01/01/2020' );

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

