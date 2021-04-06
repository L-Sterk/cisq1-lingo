# Vulnerability Analysis

## A1:2017 Injection

### Beschrijving
Bijna elke vorm van data is te benedaren via injection. Injection houd in dat je via (SQL) statements data stuurt naar de back-end van de applicatie.
Bij een SQL statement kan je een query invullen met een slecht einddoel op de database.

### Risico's
Met één SQL injection kan een heel database systeem kapot gemaakt worden. Er kan bijvoorbeeld een query meegegeven worden
waarin staat dat de database verwijderd wordt. Ook kan alle data corrupt of kwijt raken. Het kan zelfs lijden tot een totale take over.

### Oplossingen
Enkele oplossingen zijn:
<ul>
<li>Gebruik maken van een safe API zodat er geen gebruik gemaakt wordt van een parameterized interface </li>
<li>Gebruik maken van een "whitelist" zodat de speciale SQL karakters niet altijd meegegeven worden</li>
<li>Gebruik maken van LIMIT en andere SQL controls om massa injection te voorkomen</li>
</ul>

<hr>

## A2:2017 Broken Authentication

### Beschrijving
Broken Authentication houdt in dat "aanvallers" een hele lijst aan gegevens van de gebruikers
hebben. Zo kunnen ze in bezit zijn van gebruikersnamen en wachtwoorden.
De "aanvallers" beschikken over verschillende tools om aan die gegevens te komen zoals:
<ul>
<li>Credential stuffing</li>
<li>Automated attacks (brute force)</li>
<li>Dictionary attacks</li>
</ul>

De impact hiervan is dat de "aanvallers" geld kunnen witwassen of doen aan identiteits fraude.


### Risico's
Je applicatie loopt risico als het gevoelig is voor de volgende punten:
<ul>
<li>Zwakke wachtwoorden zijn toegestaan</li>
<li>Automatische aanvallen zijn toegestaan</li>
<li>Er wordt gebruik gemaakt van slecht gehashde wachtwoorden</li>
<li>Multi-factor authenticatie ontbreekt</li>

</ul>

### Oplossingen
Om de risico's te voorkomen kan je verschillende maatregelen opnemen in je applicatie:
<ul>
<li>Stel wachtwoord eisen in</li>
<li>Beperk het aantal login pogingen</li>
<li>Maak gebruik van multi-factor authenticatie (bijvoorbeeld Two-Factor Authentication)</li>
<li>Probeer te achterhalen of een login poging verdacht is</li>
<li>Session id's niet tonen in de URL</li>
</ul>

<hr>

## A9:2017 Using Components with Known Vulnerabilities

### Beschrijving
Vaak zijn ze kwetsbaarheden binnen een applicatie of een systeem al bekend bij de "aanvallers". Het is daarom goed om als ontwikkelaar deze kwetsbaarheden
goed in de gaten te houden tijdens het ontwikkelen van systemen. Vaak hebben deze kwetsbaarheden een kleine impact,
maar alsnog is het van belang om de "aanvallers" geen toegang te geven tot de voorkomende bugs.

### Risico's
Het risico van software met kwetsbaarheden is dat er gevoelige data gestolen kan worden wat kan leiden tot financiële
problemen. Ook kunnen er onopgemerkte "backdoors" achter gelaten worden door hackers.

### Oplossingen
<ul>
<li>Verwijder ongebruikte libraries, afhankelijkheden, documentatie en functies</li>
<li>Maak voor je componenten gebruik van officiele bronnen via beveiligde links</li>
<li>Zorg dat alles up-to-date is omdat daar mogelijke bugs uitgehaald zijn</li>
</ul>