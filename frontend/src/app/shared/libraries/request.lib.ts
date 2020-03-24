export class RequestLibrary {

  /**
   * @description Add parameters to URL.
   * @param parameters Object with key value pairs.
   */
  private static getUrlParameters(parameters: object): string {
    return Object.keys(parameters).map(i => `${i}=${parameters[i]}`).join('&');
  }

  /**
   * @description Generic fetch method.
   * @param url URL to call.
   * @param method REST method.
   * @param body JSON payload.
   * @param token Authentication token.
   * @param type Response type.
   */
  private static fetch(url: string, method: string, body?: object, token?: string, type: string = 'json'): Promise<any> {
    const req = {
      method,
      headers: new Headers({
        'Content-Type': 'application/json'
      }),
      body: JSON.stringify(body)
    };

    return window.fetch(url, req)
      .then((response) => {
        // If response is not ok, throw error
        if (!response.ok) {
          throw response;
        }

        return response.json();
      })
      .catch((error) => {
        return Promise.reject(error);
      });
  }

  /**
   * @description Creating a GET-request, returning JSON.
   * @param token Token for authorization.
   * @param url Complete url (excl. parameters) for the GET-request.
   * @param parameters Object of parameters in key value pairs.
   */
  static get(token: string, url: string, parameters?: object): Promise<any> {
    // Construct URL including parameters
    if (parameters) {
      url += `?${this.getUrlParameters(parameters)}`;
    }

    // Create request
    return this.fetch(url, 'GET', undefined, token);
  }

  /**
   * @description Creating a POST-request, returning JSON.
   * @param token Token for authorization.
   * @param url Complete url (excl. parameters) for the POST-request.
   * @param body Body of the request.
   * @param parameters Object of parameters in key value pairs.
   */
  static post(token: string, url: string, body: object, parameters?: object): Promise<any> {
    // Construct URL including parameters
    if (parameters) {
      url += `?${this.getUrlParameters(parameters)}`;
    }

    // Create request
    return this.fetch(url, 'POST', body, token);
  }

  /**
   * @description Creating a PUT-request, returning JSON.
   * @param token Token for authorization.
   * @param url Complete url (excl. parameters) for the POST-request.
   * @param body Body of the request.
   * @param parameters Object of parameters in key value pairs.
   */
  static put(token: string, url: string, body: object, parameters?: object): Promise<any> {
    // Construct URL including parameters
    if (parameters) {
      url += `?${this.getUrlParameters(parameters)}`;
    }

    // Create request
    return this.fetch(url, 'PUT', body, token);
  }

}
