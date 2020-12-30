﻿SELECT	c1.name, c1.latitude, c1.longitude, c2.name, c2.latitude, c2.longitude, d.distance FROM city AS c1 JOIN distance AS d ON  c1.name = d.fromcity JOIN city AS c2 ON c2.name = d.tocity WHERE c1.name = 'Москва' and c2.name = 'Санкт-Петербург'