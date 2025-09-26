export function getCookie(name) {
    let value = "; " + document.cookie;
    let parts = value.split("; " + name + "=");
    if (parts.length == 2) return parts.pop().split(";").shift() || null;
    return null
}

export function setCookie(name,value,days) {
    let expires = "";
    if (days) {
        let date = new Date();
        date.setTime(date.getTime() + (days*24*60*60*1000));
        expires = "; expires=" + date.toUTCString();
    }
    document.cookie = name + "=" + (value || "")  + expires + "; path=/";
}

//const host = "http://localhost:44621/api/"
const host = "https://api.invester.endimc.pl/api/"
function fixAddr(adr: string) {
    /*if(adr.startsWith("LEGACYAPI_")) {
        return window.location.protocol + '//' + window.location.host + '/phpscripts/api.php?f='+adr.slice(10);
    }*/
    return host+adr
}
function getSID() {
    return "WODOSPAD"
}
export function fet(address: string,options = null) {
    const isFormData = options == null || options.body == null ? false : options.body instanceof FormData

    let default_headers = isFormData ? {} : {"Content-Type":"application/json"};
    if (typeof getSID != 'undefined') {
        default_headers["Authorization"] = "Bearer "+getSID();
    }

    if(options == null) {
        options = {method:"GET",headers:default_headers};
    } else {
        if(options["headers"] == null) {
            options["headers"] = {};
        }
        const provided_options_keys = Object.keys(options["headers"]);
        Object.keys(default_headers).forEach(default_headers_key=>{
            if(!(default_headers_key in provided_options_keys)) {
                options["headers"][default_headers_key] = default_headers[default_headers_key];
            }
        })
        if(!isFormData) {
            if (options['body'] != null && typeof options['body'] === 'object') {
                options['body'] = JSON.stringify(options['body'])
            }
        }
    }

    let fetchPromise: Promise<Response> = fetch(fixAddr(address),options);

    const processPromise: Promise<String> = fetchPromise.then((response: Response) => {
        if(response.ok){
            return response.text()
        }
        if(response.status == 401) {
            location.reload()
            throw new Error(`${response.status}`)
        }
        throw new Error(`${response.status}`)
    })

    return processPromise.then((res)=>{
        try {
            return JSON.parse(res.toString())
        } catch (e) {
            return res.toString()
        }
    })
}