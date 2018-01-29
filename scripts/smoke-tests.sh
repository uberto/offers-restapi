echo greetings
curl localhost:8080

echo
echo
echo create three offers
curl -X POST http://localhost:8080/offers -d @offer1.json --header "Content-Type: application/json"
curl -X POST http://localhost:8080/offers -d @offer2.json --header "Content-Type: application/json"
curl -X POST http://localhost:8080/offers -d @offer3.json --header "Content-Type: application/json"

echo
echo
echo read an offer
curl localhost:8080/offer/OFF1 --header "Accept: application/json"

echo
echo
update an offer
curl -X PUT http://localhost:8080/offer/OFF1/edit -d @offer1v2.json --header "Content-Type: application/json"

echo
echo
echo cancel an offer
curl -X DELETE http://localhost:8080/offer/OFF2

echo
echo
echo show all valid offers
curl http://localhost:8080/offers --header "Accept: application/json"


