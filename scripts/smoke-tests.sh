#greetings
curl localhost:8080

#create an offer
curl http://localhost:8080/offers -d @offer1.json --header "Content-Type: application/json"

#read an offer
curl localhost:8080/offer/OFF1 --header "Accept: application/json"

#create an offer
curl -X POST http://localhost:8080/offers -d @offer2.json --header "Content-Type: application/json"

#create an offer
curl http://localhost:8080/offers -d @offer3.json --header "Content-Type: application/json"

#update an offer
curl -X PUT http://localhost:8080/offer/OFF1 -d @offer1v2.json --header "Content-Type: application/json"

#cancel an offer
curl -X DELETE http://localhost:8080/offer/OFF2

#show all valid offers
curl http://localhost:8080/offers --header "Accept: application/json"


