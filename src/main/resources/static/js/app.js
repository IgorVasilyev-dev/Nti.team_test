async function sendRequest(method, url,body=null) {
    const headers = {
        'Content-type': 'application/json'
    }
    if(body !== null) {
        body = JSON.stringify(body)
    }
    return await fetch(url, {
        method: method,
        body: body,
        headers: headers
    }).then(response => {
        if(response.ok) {
            if (method === 'GET') {
                return response.json()
            }
            return response.json
        } else {
            return response.json().then(error => {
                const e = new Error('Что-то пошло не так')
                e.data = error
                throw e
            })
        }

    })
}

function addPlanet(id, selector, method) {
    let inputBody = {};
    let elements = document.getElementById(id);
    let selectors =  elements.querySelectorAll(selector)
    selectors.forEach( input => {
        inputBody[input.id] = input.value
    })

    sendRequest(method, 'http://localhost:8080/planets', inputBody).then(response => {
        alert('Сохранено')
        createAddPlanetsForm('addPlanets')
    })
}

function delPlanet(planetsList,id, selector, method) {
    let inputBody = {};
    let elements = document.getElementById(id);
    let selectors =  elements.querySelectorAll(selector)
    selectors.forEach( input => {
        if(input.id === 'planetsList') {
            inputBody = planetsList.find(obj => {
                return obj.name === input.value
            })
        }
    })

    sendRequest(method, 'http://localhost:8080/planets', inputBody).then(response => {
        alert('Успешно удалено')
        createdeletePlanetsTab(planetsList,'addPlanets')
    })
}


function addLord(id, selector, method) {
    let inputBody = {};
    let selectors =  document.getElementById(id).querySelectorAll(selector)
    selectors.forEach( input => {
        inputBody[input.id] = input.value
    })

    sendRequest(method, 'http://localhost:8080/lords', inputBody).then(response => {
        alert('Сохранено')
        createAddLordsForm('addLords')
    })

}

function updateLord(lordList,planetList,id, selectors, method) {
    let inputBody = {};
    let planets = [];
    let data=[]

    let elements = document.getElementById(id);
    selectors.forEach( selectors => {
        let selector =  elements.querySelectorAll(selectors)
        selector.forEach( input => {
            if(input.id === 'lordsList') {
                inputBody = lordList.find(obj => {
                    return obj.name === input.value
                })
            }
            if(input.id === 'planetsList') {
                let planet = planetList.find(obj => {
                    return obj.name === input.value
                })
                planets.push(planet)
            }
        })
    })

    inputBody.planets = planets
    sendRequest(method, 'http://localhost:8080/lords', inputBody).then(response => {
        alert('Планета ' + planets[0].name + ' успешно добавлена под управление ' + inputBody.name)
        createUpdateLordsForm(lordList, planetList,'updateLords')
    })

}


function getElement(id) {
    return document.getElementById(id);
}

function createHtmlElements_lords_All(idElement) {
    let element = getElement(idElement);
    element.innerHTML = 'Lords-All';
}

window.addEventListener("DOMContentLoaded", () => {

    const handlerClick = (event) => {
        if (event.target.id === "lords") {
            createHtmlElementsPageLords('divBody')
        }
        if (event.target.id === "planets") {
            createHtmlElementsPagePlanets('divBody')
        }
    }

    getElement('lords').addEventListener('click', handlerClick)
    getElement('planets').addEventListener('click', handlerClick)

});

function createTable(idElement, data) {
    let element = getElement(idElement)
    element.innerHTML ="Получили всех лордов"
}

function createHtmlElementsPagePlanets(idElement) {
    let element = getElement(idElement);
    element.innerHTML = element.innerHTML = '<ul class="nav nav-tabs" id="planetsTab" role="tablist">\n' +
        '  <li class="nav-item" role="presentation">\n' +
        '    <button data-num="1" class="nav-link" id="all-planetsTab" data-bs-toggle="tab" data-bs-target="#allPlanets" type="button" role="tab" aria-controls="allPlanets" aria-selected="false" >All Planets</button>\n' +
        '  </li>\n' +
        '  <li class="nav-item" role="presentation">\n' +
        '    <button class="nav-link" data-num="2" id="add-planetsTab" data-bs-toggle="tab" data-bs-target="#addPlanets" type="button" role="tab" aria-controls="addPlanets" aria-selected="false">Add Planet</button>\n' +
        '  </li>\n' +
        '  <li class="nav-item" role="presentation">\n' +
        '    <button class="nav-link" data-num="3" id="del-planetstab" data-bs-toggle="tab" data-bs-target="#deletePlanets" type="button" role="tab" aria-controls="deletePlanets" aria-selected="false">Delete Planet</button>\n' +
        '  </li>\n' +
        '</ul>\n' +
        '<div class="tab-content" id="planetsTabContent">\n' +
        '  <div class="tab-pane fade show active" id="allPlanets" role="tabpanel" aria-labelledby="allLords-tab">Список всех планет</div>\n' +
        '  <div class="tab-pane fade" id="addPlanets" role="tabpanel" aria-labelledby="addPlanets-tab">Добавить планету</div>\n' +
        '  <div class="tab-pane fade" id="deletePlanets" role="tabpanel" aria-labelledby="deletePlanets-tab">Удалить Планету</div>\n' +
        '</div>';

    addEventClick('#planetsTab li')

}

function createHtmlElementsPageLords(idElement) {
    let element = getElement(idElement)
    element.innerHTML = '<ul class="nav nav-tabs" id="lordsTab" role="tablist">\n' +
        '  <li class="nav-item" role="presentation">\n' +
        '    <button data-num="1" class="nav-link" id="all-lordsTab" data-bs-toggle="tab" data-bs-target="#allLords" type="button" role="tab" aria-controls="allLords" aria-selected="false" >All Lords</button>\n' +
        '  </li>\n' +
        '  <li class="nav-item" role="presentation">\n' +
        '    <button class="nav-link" id="top10-lordsTab" data-bs-toggle="tab" data-bs-target="#top10Lords" type="button" role="tab" aria-controls="top10Lords" aria-selected="false">Top10 Lords</button>\n' +
        '  </li>\n' +
        '  <li class="nav-item" role="presentation">\n' +
        '    <button class="nav-link" id="empty-lordstab" data-bs-toggle="tab" data-bs-target="#emptyLords" type="button" role="tab" aria-controls="emptyLords" aria-selected="false">Empty Lords</button>\n' +
        '  </li>\n' +
        '  <li class="nav-item" role="presentation">\n' +
        '    <button class="nav-link" id="add-lordstab" data-bs-toggle="tab" data-bs-target="#addLords" type="button" role="tab" aria-controls="addLords" aria-selected="false">Add Lord</button>\n' +
        '  </li>\n' +
        '  <li class="nav-item" role="presentation">\n' +
        '    <button class="nav-link" id="update-lordstab" data-bs-toggle="tab" data-bs-target="#updateLords" type="button" role="tab" aria-controls="updateLords" aria-selected="false">AddPlanet</button>\n' +
        '  </li>\n' +
        '</ul>\n' +
        '<div class="tab-content" id="lordsTabContent">\n' +
        '  <div class="tab-pane fade show active" id="allLords" role="tabpanel" aria-labelledby="allLords-tab">Отобразить всех Повелителей</div>\n' +
        '  <div class="tab-pane fade" id="top10Lords" role="tabpanel" aria-labelledby="top10Lords-tab">Отобразить ТОП 10 самых молодых Повелителей</div>\n' +
        '  <div class="tab-pane fade" id="emptyLords" role="tabpanel" aria-labelledby="emptyLords-tab">Найти всех Повелителей бездельников, которые прохлаждаются и не управляют никакими Планетами</div>\n' +
        '  <div class="tab-pane fade" id="addLords" role="tabpanel" aria-labelledby="addLords-tab">Добавить нового Повелителя</div>\n' +
        '  <div class="tab-pane fade" id="updateLords" role="tabpanel" aria-labelledby="updateLords-tab">Назначить Повелителя управлять Планетой</div>\n' +
        '</div>';

    addEventClick('#lordsTab li')

}

function addEventClick(idSelector) {
    const handlerClick = (event) => {
        switch (event.target.id) {
            case 'all-planetsTab':
                sendRequest('GET','http://localhost:8080/planets').then(response => {
                    createPlanetsTab(response)
                })
                break
            case 'add-planetsTab':
                createAddPlanetsForm('addPlanets')
                break
            case 'del-planetstab':
                sendRequest('GET','http://localhost:8080/planets').then(response => {
                    createdeletePlanetsTab(response, 'deletePlanets')
                })
                break
            case 'all-lordsTab':
                sendRequest('GET','http://localhost:8080/lords').then(response => {
                    createLordsTab(response, 'allLords', 'resultTableLords')
                })
                break
            case 'top10-lordsTab':
                sendRequest('GET','http://localhost:8080/lords/top').then(response => {
                    createLordsTab(response, 'top10Lords', 'resultTop10TableLords')
                })
                break
            case 'empty-lordstab':
                sendRequest('GET','http://localhost:8080/lords/empty').then(response => {
                    createLordsTab(response, 'emptyLords', 'resultEmptyTableLords')
                })
                break
            case 'add-lordstab':
                createAddLordsForm('addLords')
                break
            case 'update-lordstab':
                sendRequest('GET','http://localhost:8080/planets').then(planetList => {
                    sendRequest('GET','http://localhost:8080/lords').then(lordList => {
                        createUpdateLordsForm(lordList, planetList,'updateLords')
                    })
                })
                break
        }
    }

    let tableNav = document.querySelectorAll(idSelector)
    tableNav.forEach(button => {
        button.addEventListener('click', handlerClick)
    });

}

function createPlanetsTab(data) {
    let element = getElement('allPlanets')
    let count = 1;
    element.innerHTML = '<table class="table">\n' +
        '  <thead>\n' +
        '    <tr>\n' +
        '      <th scope="col">#</th>\n' +
        '      <th scope="col">Name</th>\n' +
        '    </tr>\n' +
        '  </thead>\n' +
        '  <tbody id="resultTablePlanets">\n' +
        '  </tbody>\n' +
        '</table>';
    data.forEach(e => {
        let tr = document.createElement('tr')
        tr.innerHTML = '<tr>\n' +
            '      <th scope="row">' + count++ +'</th>\n' +
            '      <td>' + e.name +'</td>\n' +
            '    </tr>';
        document.getElementById('resultTablePlanets').appendChild(tr)
    })
}

function createAddPlanetsForm(idElement) {

    let element = getElement(idElement)
    element.innerHTML = '<form id="formAddPlanet" class="row g-3"  >\n' +
        '<div class="col-md-4">\n' +
        '    <label for="name" class="form-label">Planet name</label>\n' +
        '    <input type="text" class="form-control" id="name">\n' +
        '</div>\n' +
        '</div>' +
        '  <div class="col-12">\n' +
        '    <button type="submit" id="addPlanet_btn" class="btn btn-primary">сохранить</button>\n' +
        '  </div>\n' +
        '</form>';

    let button = document.querySelector('#addPlanet_btn')
    const handlerClick = (event) => {
        addPlanet("formAddPlanet","input","POST")
    }
    button.addEventListener('click', handlerClick)
}

function  createdeletePlanetsTab(planetList, idElement) {

    let element = getElement(idElement)
    element.innerHTML = '<form id="formDeletePlanet" class="row g-3"  >\n' +
        '<div class="col-md-4">\n' +
        '    <label for="planetsList" class="form-label">Planets</label>\n' +
        '    <select id="planetsList" class="form-select">\n' +
        '     <option selected>select planet</option>' +
        '    </select>\n' +
        '</div>' +
        '  <div class="col-12">\n' +
        '    <button type="submit" id="deletePlanet_btn" class="btn btn-primary">удалить</button>\n' +
        '  </div>\n' +
        '</form>';

    getOptions(planetList, 'planetsList')

    let button = document.querySelector('#deletePlanet_btn')
    const handlerClick = (event) => {
        delPlanet(planetList,"formDeletePlanet","select","DELETE")
    }
    button.addEventListener('click', handlerClick)
}

function createLordsTab(data, idElement, idtable) {
    let element = getElement(idElement)
    let count = 1;
    element.innerHTML = '<table class="table">\n' +
        '  <thead>\n' +
        '    <tr>\n' +
        '      <th scope="col">#</th>\n' +
        '      <th scope="col">Name</th>\n' +
        '      <th scope="col">Age</th>\n' +
        '      <th scope="col">Planets Under Control</th>\n' +
        '    </tr>\n' +
        '  </thead>\n' +
        '  <tbody id=' + idtable + '>\n' +
        '  </tbody>\n' +
        '</table>';
    data.forEach(e => {
        let planetsList = [];
        e.planets.forEach(planets => {
            planetsList.push(planets.name)
        })
        let tr = document.createElement('tr')
        tr.innerHTML = '<tr>\n' +
            '      <th scope="row">' + count++ +'</th>\n' +
            '      <td>' + e.name +'</td>\n' +
            '      <td>' + e.age +'</td>\n' +
            '      <td>' + planetsList +'</td>\n' +
            '    </tr>';
        document.getElementById(idtable).appendChild(tr)
    })
}

function createAddLordsForm(idElement) {

    let element = getElement(idElement)
    element.innerHTML = '<form id="formAddLord" class="row g-3"  >\n' +
        '<div class="col-md-4">\n' +
        '    <label for="name" class="form-label">Lord name</label>\n' +
        '    <input type="text" class="form-control" id="name">\n' +
        '</div>\n' +
        '<div class="col-md-4">\n' +
        '    <label for="age" class="form-label">Lord age</label>\n' +
        '    <input type="number" class="form-control" id="age">\n' +
        '</div>\n' +
        '</div>' +
        '  <div class="col-12">\n' +
        '    <button type="submit" id="addLord_btn" class="btn btn-primary">сохранить</button>\n' +
        '  </div>\n' +
        '</form>';

    let button = document.querySelector('#addLord_btn')
    const handlerClick = (event) => {
        addLord("formAddLord","input","POST")
    }
    button.addEventListener('click', handlerClick)
}

function createUpdateLordsForm(lordList,planetList,idElement) {

    let element = getElement(idElement)
    element.innerHTML = '<form id="formUpdateLord" class="row g-3"  >\n' +
        '<div class="col-md-4">\n' +
        '    <label for="lordsList" class="form-label">Lords</label>\n' +
        '    <select id="lordsList" class="form-select">\n' +
        '     <option selected>select lord</option>' +
        '    </select>\n' +
        '</div>' +
        '<div class="col-md-4">\n' +
        '    <label for="planetsList" class="form-label">Planets</label>\n' +
        '    <select id="planetsList" class="form-select">\n' +
        '     <option selected>select planet</option>' +
        '    </select>\n' +
        '</div>' +
        '  <div class="col-12">\n' +
        '    <button type="submit" id="updateLord_btn" class="btn btn-primary">сохранить</button>\n' +
        '  </div>\n' +
        '</form>';

    getOptions(lordList, 'lordsList')
    getOptions(planetList, 'planetsList')

    let button = document.querySelector('#updateLord_btn')
    const handlerClick = (event) => {
        updateLord(lordList,planetList,"formUpdateLord",["input","select"],"PUT")
    }
    button.addEventListener('click', handlerClick)
}

function getOptions(data, element) {
    data.forEach((e) => {
        let options = document.createElement("option")
        options.innerHTML = '<option>' + e.name + '</option>'
        document.getElementById(element).appendChild(options)
    })}