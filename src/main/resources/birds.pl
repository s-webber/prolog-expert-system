% This program is based on the Birds expert system from Adventure in Prolog by Dennis Merritt.
% ISBN-13: 978-1520918914. Independently published. 10 November 2017.
% http://www.amzi.com/AdventureInProlog/appendix.php#Birds

% In this version the menuask predicate is implemented as a built-in predicate using:
% org.projog.expert.RulesEngine.AskUserPredicate.
% RulesEngine is created in org.projog.expert.ExpertSystemApplication.

% BIRDS

% This is a sample of a classification expert system for identification
% of certain kinds of birds. The rules are rough excerpts from "Birds of
% North America" by Robbins, Bruum, Zim, and Singer.

% This type of expert system can easily use Prolog's built in inferencing
% system. While trying to satisfy th
% various subgoals, some of which will ask for information from the
% user.

% The information is all stored as attribute-value pairs. The attribute
% is represented as a predicate, and the value as the argument to the
% predicate. For example, the attribute-value pair "color-brown" is
% stored "color(brown)".

% The rules of identification are the bulk of the code. They break up
% the problem into identifying orders and families before identifying
% the actual birds.

order(tubenose):-
  nostrils('external tubular'),
  live('at sea'),
  bill(hooked).
order(waterfowl):-
  feet(webbed),
  bill(flat).
order(falconiforms):-
  % EDIT commented out eats(meat), and added it to order(falconiforms), else 'sparrow hawk' and 'peregrine falcon' not identifiable
  % eats(meat)
  feet('curved talons'),
  bill('sharp hooked').
order(passerformes):-
  feet('one long backward toe').

family(albatross):-
  order(tubenose),
  size(large),
  wings('long narrow').
family(swan):-
  order(waterfowl),
  neck(long),
  color(white),
  flight(ponderous).
family(goose):-
  order(waterfowl),
  size(plump),
  flight(powerful).
family(duck):-
  order(waterfowl),
  feed('on water surface'),
  flight(agile).
family(vulture):-
  order(falconiforms),
  % EDIT moved eats(meat) here from order(falconiforms)
  eats(meat),
  feed(scavange),
  wings(broad).
family(falcon):-
  order(falconiforms),
  wings('long pointed'),
  head(large),
  tail('narrow at tip').
family(flycatcher):-
  order(passerformes),
  bill(flat),
  eats('flying insects').
family(swallow):-
  order(passerformes),
  wings('long pointed'),
  % EDIT commented out tail(forked) else all swallows are clasified as 'barn swallow'
  % tail(forked)
  bill(short).

bird('laysan albatross'):-
  family(albatross),
  color(white).
bird('black footed albatross'):-
  family(albatross),
  color(dark).
bird(fulmar):-
  order(tubenose),
  size(medium),
  flight('flap glide').
bird('whistling swan'):-
  family(swan),
  voice('muffled musical whistle').
bird('trumpeter swan'):-
  family(swan),
  voice('loud trumpeting').
bird('canada goose'):-
  family(goose),
  season(winter),                % rules can be further broken down
  country('united states'),      % to include regions and migration
  head(black),                   % patterns
  cheek(white).
bird('canada goose'):-
  family(goose),
  season(summer),
  country(canada),
  head(black), 
  cheek(white).
bird('snow goose'):-
  family(goose),
  color(white).
bird(mallard):-
  family(duck),                  % different rules for male
  voice(quack),
  head(green).
bird(mallard):-
  family(duck),                  % and female
  voice(quack),
  color('mottled brown').
bird(pintail):-
  family(duck),
  voice('short whistle').
bird('turkey vulture'):-
  family(vulture),
  flight_profile('v shaped').
bird('california condor'):-
  family(vulture),
  flight_profile(flat).
bird('sparrow hawk'):-
  family(falcon),
  eats(insects).
bird('peregrine falcon'):-
  family(falcon),
  eats(birds).
bird('great crested flycatcher'):-
  family(flycatcher),
  tail('long rusty').
bird('ash throated flycatcher'):-
  family(flycatcher),
  throat(white).
bird('barn swallow'):-
  family(swallow),
  tail(forked).
bird('cliff swallow'):-
  family(swallow),
  tail(square).
bird('purple martin'):-
  family(swallow),
  color(dark).

country('united states'):- region('new england').
country('united states'):- region('south east').
% EDIT commented out next four rules as not possible, as no matching region clause
% country('united states'):- region('mid west').
% country('united states'):- region('south west').
% country('united states'):- region('north west').
% country('united states'):- region('mid atlantic').

country(canada):- province(ontario).
country(canada):- province(quebec).

% EDIT replaced usage of member/2 with memberchk/2 as only require a single solution

region('new england'):-
  state(X),
  memberchk(X, [massachusetts, vermont]).
region('south east'):-
  state(X),
  memberchk(X, [florida, mississippi]).

region(canada):-
  province(X),
  memberchk(X, [ontario,quebec]).

nostrils(X):- menuask(nostrils,X,['external tubular']).
live(X):- menuask(live,X,['at sea']).
bill(X):- menuask(bill,X,[flat,short,'sharp hooked',hooked]).
size(X):- menuask(size,X,[large,plump,medium,small]).
eats(X):- menuask(eats,X,[birds,insects,'flying insects',meat]).
feet(X):- menuask(feet,X,['curved talons','one long backward toe',webbed]).
wings(X):- menuask(wings,X,[broad,'long broad','long pointed','long narrow']).
neck(X):- menuask(neck,X,[long]).
color(X):- menuask(color,X,[dark,'mottled brown',white]).
flight(X):- menuask(flight,X,[ponderous,powerful,agile,'flap glide']).
feed(X):- menuask(feed,X,[scavange,'on water surface']).
head(X):- menuask(head,X,[black,green,large]).
tail(X):- menuask(tail,X,['narrow at tip',forked,'long rusty',square]).
voice(X):- menuask(voice,X,['muffled musical whistle','loud trumpeting',quack,'short whistle']).
season(X):- menuask(season,X,[winter,summer]).
cheek(X):- menuask(cheek,X,[white]).
flight_profile(X):- menuask('flight profile',X,[flat,'v shaped']).
throat(X):- menuask(throat,X,[white]).
% EDIT removed etc option for state and province
state(X):- menuask(state,X,[massachusetts,vermont,florida,mississippi]).
province(X):- menuask(province,X,[ontario,quebec]).

